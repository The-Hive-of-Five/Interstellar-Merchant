package com.cs2340.interstellarmerchant.model.universe.time

import java.lang.IllegalArgumentException

class TimeController {
    companion object {
        fun dayToString(day: Int): String {
            return "$day  sol"
        }
    }

    private var currentDay: Int = 0
    get() {
        return currentDay
    }


    private val subscribers: MutableSet<TimeSubscriberI>

    init {
        this.subscribers = LinkedHashSet()
    }

    /**
     * subscribes the subscriber to time changes
     * @param subscriber - the subscriber to the event
     */
    fun subscribeToTime(subscriber: TimeSubscriberI) {
        this.subscribers.add(subscriber)
    }

    /**
     * unsubscribes the subscriber from time changes
     * @param subscriber - should be current subscribes
     */
    fun unsubscribeToTime(subscriber: TimeSubscriberI) {
        if (!subscribers.contains(subscriber)) {
            throw IllegalArgumentException("The subscriber is not subscribed to to time changes")
        }
        subscribers.remove(subscriber)

        // call the unsubscribe function on the subscriber
        subscriber.unsubscribe(currentDay)

    }

    fun timeJump(timeJump: Int) {
        if (timeJump > 0) {
            currentDay += timeJump
            timeUpdated()
        }
    }

    private fun timeUpdated() {
        // the time has been updated. tell the subscribers
        for (subscriber: TimeSubscriberI in subscribers) {
            // call the day updated method. if it returns false, unsubscribe the subscriber
            if (!subscriber.dayUpdated(currentDay)) {
                unsubscribeToTime(subscriber)
            }
        }
    }

    override fun toString(): String {
        return TimeController.dayToString(currentDay);
    }
}
