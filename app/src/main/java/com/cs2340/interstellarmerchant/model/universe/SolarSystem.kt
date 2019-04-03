package com.cs2340.interstellarmerchant.model.universe


import com.cs2340.interstellarmerchant.model.universe.planet.Planet
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech
import com.cs2340.interstellarmerchant.model.utilities.AfterDeserialized
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
                       var x: Int? = null, var y: Int? = null): AfterDeserialized, Serializable {
    val name = planets[0].name

    init {
        setPlanetLocation(planets)
    }

    fun getPlanet(planetName: String): Planet {
        var output: Planet? = null
        for (planet: Planet in planets) {
            if (planet.name == planetName) {
                output = planet
                break
            }
        }
        return output!!
    }


    companion object {
        private const val MAX_SOLAR_SIZE: Int = 6
        const val MAX_X: Int = 500
        const val MAX_Y: Int = 500

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

    override fun afterDeserialized() {
        for (planet: Planet in planets) {
            planet.solarSystem = this
            planet.afterDeserialized()
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("Solar System: $name @ <$x,$y>")
        builder.appendln("Tech level: $tech")
        for (planet: Planet in planets) {
            builder.append(planet.toString())
        }
        return builder.toString()
    }


    /**
     * Gives every planet a unique x and y
     */
    private fun setPlanetLocation(planets: MutableList<Planet>) {
        val locations = HashSet<Pair<Int, Int>>()
        val random = Random()
        for (planet: Planet in planets) {
            var pair: Pair<Int, Int>

            // ensures a unique coordinate
            do {
                val x = random.nextInt(SolarSystem.MAX_X)
                val y = random.nextInt(SolarSystem.MAX_Y)
                pair = Pair(x, y)
            } while (locations.contains(pair))

            // modify the solar system
            planet.x = pair.first
            planet.y = pair.second
            planet.solarSystem = this

            // ensures the location is not reused
            locations.add(pair)
        }
    }


}