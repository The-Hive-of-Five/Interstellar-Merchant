package com.cs2340.interstellarmerchant.model.universe.time

import java.lang.IllegalArgumentException
import javax.inject.Singleton

@Singleton
class TimeController {
    companion object {
        private var timeController: TimeController? = null

        fun dayToString(day: Int): String {
            return "$day  sol"
        }

        /**
         * Gets the timecontroller
         */
        fun getTimeController(): TimeController {
            if (timeController == null) {
                timeController = TimeController()
            }
            return timeController!!
        }

        /**
         * Used for serialization when the timecontroller has been saved.
         */
        fun setInstance(timeController: TimeController) {
            this.timeController = timeController
        }
    }

    private var currentDay = 0

    @Transient
    private val subscribers: MutableSet<TimeSubscriberI>

    init {
        this.subscribers = LinkedHashSet()
    }

    /**
     * Gets the current day
     */
    fun getCurrentDay(): Int {
        return currentDay
    }

    fun isSubscribed(item: TimeSubscriberI): Boolean {
        return subscribers.contains(item)
    }

    /**
     * subscribes the subscriber to time changes
     * @param subscriber - the subscriber to the event
     */
    fun subscribeToTime(subscriber: TimeSubscriberI) {
        if (this.subscribers.add(subscriber)) subscriber.onSubscribe(currentDay, this)
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
        // the time has been updated. tell the subscribers
        for (subscriber: TimeSubscriberI in subscribers) {
            // call the day updated method. if it returns false, onUnsubscribe the subscriber
            if (!subscriber.dayUpdated(currentDay, this)) {
                unsubscribeToTime(subscriber)
            }
        }
    }

    override fun toString(): String {
        return TimeController.dayToString(currentDay);
    }
}
