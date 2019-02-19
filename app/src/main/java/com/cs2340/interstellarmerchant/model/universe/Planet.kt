package com.cs2340.interstellarmerchant.model.universe

import org.w3c.dom.Element
import java.io.InputStream
import java.io.Serializable
import java.lang.NumberFormatException
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

data class Planet (val climate: String, val diameter: Long?, val gravity: String, val name: String,
                   val population: Long?, val rotationPeriod: Int?,
                   val resource: Resource = Resource.getRandomResource(),
                   var tech: Tech = Tech.getRandomTech()): Serializable {

    companion object {
        private fun createPlanetFromNode(xmlNode: Element): Planet {
            val climate = pullTextFromNode(xmlNode, "climate")

            var diameter: Long?
            try {
                diameter = pullTextFromNode(xmlNode, "diameter").toLong()
            } catch (ex: NumberFormatException) {
                diameter = null
            }

            val gravity = pullTextFromNode(xmlNode, "gravity")
            val name = pullTextFromNode(xmlNode, "name")

            var population: Long?
            try {
                population = pullTextFromNode(xmlNode, "population").toLong()
            } catch (ex: NumberFormatException) {
                population = null
            }

            var rotationPeriod: Int?
            try {
                rotationPeriod = pullTextFromNode(xmlNode, "rotation_period").toInt()
            } catch (ex: NumberFormatException) {
                rotationPeriod = null
            }

            return Planet(climate, diameter, gravity, name, population,
                    rotationPeriod)
        }

        private fun pullTextFromNode(xmlNode: Element, attr: String): String {
            return xmlNode.getElementsByTagName(attr).item(0).textContent
        }

        fun generatePlanets(fileInputStream: InputStream): List<Planet> {
            val planets = LinkedList<Planet>()
            try {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc = dBuilder.parse(fileInputStream)
                doc.documentElement.normalize()

                val nList = doc.getElementsByTagName("planet")

                // iterate through every planet
                for (nodeCounter in 0 until nList.length) {
                    // convert the node to a planet
                    planets.add(Planet.createPlanetFromNode(
                            nList.item(nodeCounter) as Element))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return planets
        }
    }

    override fun toString(): String {
        return toString(false)
    }

    /**
     * @param detailed - whether you want a detailed to string
     */
    fun toString(detailed: Boolean): String {
        val builder: StringBuilder = StringBuilder()
        builder.appendln("Planet: $name with resource, $resource")
        if (detailed) {
            builder.appendln("Climate: $climate")
            builder.appendln("Diameter: $diameter")
            builder.appendln("Gravity: $gravity")
            builder.appendln("Population: $population")
            builder.appendln("Rotation Period: $rotationPeriod")
        }
        return builder.toString()
    }
}