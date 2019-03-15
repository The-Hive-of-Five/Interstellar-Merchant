package com.cs2340.interstellarmerchant.model.universe.time

/**
 * TimeSubscribers must be manually resubscribed to the timecontroller after deserialization
 */
interface TimeSubscriberI {
    /**
     * @return true if the subscription should continue
     */
    fun dayUpdated(day: Int): Boolean

    /**
     * Called after the event unsubscribes
     */
    fun unsubscribe(day: Int)
}