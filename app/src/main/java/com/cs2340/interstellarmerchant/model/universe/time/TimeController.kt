package com.cs2340.interstellarmerchant.model.universe.time

import java.lang.IllegalArgumentException

/**
 * Works in a life cycle
 *
 * 1. time changes
 * 2. iterate through the queue of subscribers to be subscribed and subscribe them
 * 3. iterate through all the subscribers and call their time changed method
 * 4. if the subscribers try to subscribe new events during a lifecycle, they will be added
 * to the queue to be subscribed
 */
class TimeController {
    companion object {
        fun dayToString(day: Int): String {
            return "$day  sol"
        }

    }

    private var currentDay = 0

    @Transient
    private val subscribers: MutableSet<TimeSubscriberI>

    @Transient
    private val subscribeToTimeQueue: MutableSet<TimeSubscriberI>

    init {
        this.subscribers = LinkedHashSet()
        this.subscribeToTimeQueue = LinkedHashSet()
    }

    /**
     * Gets the current day
     */
    fun getCurrentDay(): Int {
        return currentDay
    }

    fun isSubscribed(item: TimeSubscriberI): Boolean {
        return subscribers.contains(item) || subscribeToTimeQueue.contains(item)
    }

    /**
     * subscribes the subscriber to time changes. the time controller
     * operates in a life cycle so first the subscribers are put in a queue in case
     * a day life is already occurring
     * @param subscriber - the subscriber to the event
     */
    fun subscribeToTime(subscriber: TimeSubscriberI) {
        if (this.subscribers.contains(subscriber) ||
                this.subscribeToTimeQueue.contains(subscriber)) {
            throw IllegalArgumentException("The subscriber is already subscribed to time;")
        }
        subscribeToTimeQueue.add(subscriber)
        subscriber.onSubscribe(currentDay, this)
    }

    /**
     * unsubscribes the subscriber from time changes
     * @param subscriber - should be current subscribes
     */
    private fun unsubscribeFlag(subscriber: TimeSubscriberI) {
        if (!subscribers.contains(subscriber)) {
            throw IllegalArgumentException("The subscriber is not subscribed to to time changes")
        }
        // call the onUnsubscribe function on the subscriber
        subscriber.onUnsubscribe(currentDay, this)
    }

    fun timeJump(timeJump: Int) {
        for (counter in 1..timeJump) {
            currentDay++
            timeUpdated()
        }
    }

    private fun timeUpdated() {
        // subscribe all the subscribers who have been queued to subscribe
        subscribeQueuedSubscribers()

        // the time has been updated. tell the subscribers
        val iterator = subscribers.iterator()
        while (iterator.hasNext()) {
            val subscriber = iterator.next()
            // call the day updated method. if it returns false, onUnsubscribe the subscriber
            if (!subscriber.dayUpdated(currentDay, this)) {
                unsubscribeFlag(subscriber)
                iterator.remove()
            }
        }
    }

    private fun subscribeQueuedSubscribers() {
        val iterator = subscribeToTimeQueue.iterator()
        while (iterator.hasNext()) {
            val subscriber = iterator.next()
            subscribers.add(subscriber)
            iterator.remove()
        }

    }

    override fun toString(): String {
        return TimeController.dayToString(currentDay)
    }
}
