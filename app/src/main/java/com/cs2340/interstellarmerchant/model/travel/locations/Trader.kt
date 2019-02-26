package com.cs2340.interstellarmerchant.model.travel.locations

import com.cs2340.interstellarmerchant.model.travel.Location
import com.cs2340.interstellarmerchant.model.universe.SolarSystem

/**
 * Trader class. A location in the universe
 */
class Trader: Location() {
    companion object {
        /**
         * Gets a random trader
         *
         * @return the random trader
         */
        fun generateRandomTrader(): Trader {
            return null
        }
    }

    override fun getX(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getY(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocationType(): LocationType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSolarSystem(): SolarSystem {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}