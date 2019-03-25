package com.cs2340.interstellarmerchant.model.universe.time

/**
 * TimeSubscribers must be manually resubscribed to the timecontroller after deserialization
 */
interface TimeSubscriberI {
    /**
     * @return true if the subscription should continue
     */
    fun dayUpdated(day: Int, timeController: TimeController): Boolean

    /**
     * Called when the event is first subscribed to the time controller
     */
    fun onSubscribe(day: Int, timeController: TimeController) {
        // does nothing by default
    }
    /**
     * Called after the event unsubscribes
     */
    fun onUnsubscribe(day: Int, timeController: TimeController)
}