package com.cs2340.interstellarmerchant.model.universe

import java.io.Serializable
import java.util.*

/**
 * Solar system data class
 *
 * @param planets - the planets in the solar system
 * @param tech - the tech level of the solar system (random if not set)
 * @param x - the x location of the solar system; null by default
 * @param y - the y locatino of the solar system; null by default
 */
data class SolarSystem(val planets: MutableList<Planet>, val tech: Tech = Tech.getRandomTech(),
                       var x: Int? = null, var y: Int? = null): Serializable {
    val name = planets[0].name

    init {
        syncPlanets(planets)
    }


    companion object {
        const val MAX_SOLAR_SIZE: Int = 6

        /**
         * Generates a list of random solar systems with random planets
         *
         * @param planets - planets to seed the solar system generation
         */
        fun generateSolarSystem(planets: MutableList<Planet>): MutableList<SolarSystem> {
            val solarSystems: MutableList<SolarSystem> = LinkedList()
            while (!planets.isEmpty()) {
                // determine the number of planets in the solar system
                var solarSize = (Math.random() * MAX_SOLAR_SIZE).toInt() + 1

                // if number of planets larger than number available, use number available
                solarSize = if (solarSize > planets.size) planets.size else solarSize

                val solarPlanets: MutableList<Planet> = LinkedList()
                while (solarSize > 0) {
                    // remove a random planet
                    val randomPlanet: Planet = planets.removeAt(
                            (Math.random() * planets.size).toInt())
                    solarPlanets.add(randomPlanet)
                    solarSize--
                }
                solarSystems.add(SolarSystem(solarPlanets))

            }
            return solarSystems
        }
    }
    override fun toString(): String {
        val builder: StringBuilder = StringBuilder()
        builder.appendln("Solar System: $name @ <$x,$y>")
        builder.appendln("Tech level: $tech")
        for (planet: Planet in planets) {
            builder.append(planet.toString())
        }
        return builder.toString()
    }

    /**
     * syncs the tech levels of the planets with the tech levels of the universe
     */
    private fun syncPlanets(planet: MutableList<Planet>) {
        for (planet: Planet in planets) {
            planet.tech = tech
        }
    }

}