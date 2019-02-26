package com.cs2340.interstellarmerchant.model.travel.locations

import com.cs2340.interstellarmerchant.model.travel.Location
import java.util.*
import javax.inject.Singleton

/**
 * Location factory for random, non-planet locations, like traders and space tolls
 */
@Singleton
class LocationFactory {
    /**
     * Gets a location
     *
     * @param locationType - the type of location
     * @return the location
     */
    fun getLocation(locationType: String): Location {
        var output: Location
        when (locationType) {
            "trader" -> {
                output = Trader.generateRandomTrader()
            } else -> {
                output = SpaceToll.generateRandomToll()
            }
        }
        return output
    }

    /**
     * Gets a random location
     *
     * @return the random location
     */
    fun getRandomLocation(): Location {
        var output: Location
        if (Random().nextBoolean()) {
            output = Trader.generateRandomTrader()
        } else {
            output = SpaceToll.generateRandomToll()
        }

        return output
    }
}