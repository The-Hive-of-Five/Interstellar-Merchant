package com.cs2340.interstellarmerchant.model.travel

import kotlin.math.roundToInt

/**
 * Represents a Trip
 *
 * @param startingLocation - the location the trip starts from
 * @param endingLocation - the location where the trip ends
 */
data class Trip(val startingLocation: Location, val endingLocation: Location) {
    companion object {
        const val SOLAR_MULTIPLIER = 2.0
        const val PLANET_MULTIPLIER = .5

        // this might need to be adjusted
        const val FUEL_TO_TIME = .05

        /**
         * The distance formula
         */
        fun distance(xOne: Int, yOne: Int, xTwo: Int, yTwo: Int): Double {
            val xDiff = xOne - xTwo
            val yDiff = yOne - yTwo

            val xPow = Math.pow(xDiff.toDouble(), 2.0)
            val yPow = Math.pow(yDiff.toDouble(), 2.0)

            val sum = xPow + yPow

            return Math.sqrt(sum)
        }
    }

    var fuelCost: Int? = null
    var time: Int? = null

    init {
        this.fuelCost = determineCost()
        this.time = determineTime()
    }

    fun getTripLog(): TripLog {
        if (fuelCost == null || time == null) {
            throw IllegalStateException("Can't output fuel cost and time if they have not" +
                    "already been calculated")
        }
        return TripLog(fuelCost!!, time!!)
    }

    /**
     * Determines the cost of the trip
     *
     * @return the trip cost
     */
    private fun determineCost(): Int {
        var xOne: Int
        var yOne: Int
        var xTwo: Int
        var yTwo: Int
        var multiplier: Double
        if (startingLocation.solarSystem == endingLocation.solarSystem) {
            // if in the same solar system, use the actual distance between the locations
            xOne = startingLocation.x
            yOne = startingLocation.y
            xTwo = endingLocation.x
            yTwo = endingLocation.y
            multiplier = Trip.PLANET_MULTIPLIER
        } else {
            // if in different solar systems, just use the distances between solar systems
            val startingSystem = startingLocation.solarSystem
            val endingSystem = endingLocation.solarSystem
            xOne = startingSystem.x!!
            yOne = startingSystem.y!!
            xTwo = endingSystem.x!!
            yTwo = endingSystem.y!!
            multiplier = Trip.SOLAR_MULTIPLIER
        }
        val distance = distance(xOne, yOne, xTwo, yTwo)
        return (distance * multiplier).roundToInt()
    }

    /**
     * Determines the time a trip takes
     */
    private fun determineTime(): Int {
        return (this.fuelCost!! * Trip.FUEL_TO_TIME).toInt()
    }

}