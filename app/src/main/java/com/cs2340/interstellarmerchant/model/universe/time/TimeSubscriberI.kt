package com.cs2340.interstellarmerchant.model.universe.time

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