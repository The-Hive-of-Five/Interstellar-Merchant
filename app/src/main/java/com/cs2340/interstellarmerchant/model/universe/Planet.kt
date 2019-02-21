package com.cs2340.interstellarmerchant.model.universe

import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent
import com.cs2340.interstellarmerchant.model.universe.market.Economy
import com.cs2340.interstellarmerchant.model.universe.market.Market
import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import com.cs2340.interstellarmerchant.model.universe.market.items.Order
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech
import org.w3c.dom.Element
import java.io.InputStream
import java.io.Serializable
import java.util.*
import java.util.stream.Collectors
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.math.roundToInt

/**
 * planet data class
 *
 * @param climate - the cliamte of the planet
 * @param diameter - the diameter of the planet. can be null
 * @param gravity - the gravity of the planet as a string
 * @param name - the name of the planet
 * @param population - the population of the planet. can be null
 * @param rotationPeriod - the rotation period of the planet; can be null
 * @param resource - the resource of the planet. will be randomly selected if not explicitly given
 * @pparam tech - the tech of the planet. will be randomly selected if not explicitly given
 */
data class Planet (val climate: String, val diameter: Long?, val gravity: String, val name: String,
                   val population: Long?, val rotationPeriod: Int?,
                   val resource: Resource = Resource.getRandomResource(),
                   var tech: Tech = Tech.getRandomTech()): Economy, Serializable {

    private val currentEvents = HashSet<PlanetEvent>()

    // market must be initialized after current events
    val market = Market(this)


    init {


    }

    override fun canBuyItems(order: Order?): OrderStatus {
        var output: OrderStatus
        if (order!!.minSellTech!! > tech) {
            output = OrderStatus.NOT_ENOUGH_TECH
        } else {
            output = OrderStatus.SUCCESS
        }
        return output;
    }

    override fun canBuyItem(item: Item?, quantity: Int): OrderStatus {
        var orderStatus: OrderStatus
        if (item!!.sellTechLevel > tech) {
            orderStatus = OrderStatus.NOT_ENOUGH_TECH;
        } else {
            orderStatus = OrderStatus.SUCCESS
        }
        return orderStatus
    }

    override fun filterItems(potentialItems: List<Item>): MutableList<Item>? {
        val random = Random()
        val chanceInStore = 80
        return potentialItems
                .stream()
                .filter { item: Item ->
                    var output = item.produceTechLevel <= tech
                    output = output && random.nextInt(100) < chanceInStore

                    return@filter output
                }
                .collect(Collectors.toList())
    }

    override fun calculateQuantity(item: Item?): Int {
        val random = Random()
        var factor: Int
        var signFactor: Int
        var minVariance: Int
        var maxVariance: Int
        if (item!!.idealTechLevel == tech) { // increase quantity if ideal tech level
            minVariance = 50
            maxVariance = 150
            signFactor = 1
        } else {
            signFactor = if (random.nextBoolean()) -1 else 1
            minVariance = 10
            maxVariance = 90
        }

        factor = random.nextInt(maxVariance - minVariance) + minVariance
        val netVariance = signFactor * ((factor.toDouble()/100.0) * item.baseQuantity).roundToInt()
        return item.baseQuantity + netVariance
    }

    /**
     * Used for calculating the price of items in its economy
     */
    override fun calculatePrice(item: Item?): Int {
        var price: Int
        if (item != null) {
            price = item.base

            // account for tech level difference
            price += (tech.ordinal - item.sellTechLevel.ordinal) * item.priceIncreasePerTech

            val random = Random()
            var factor: Int
            when {
                item.decreaseResource == this.resource -> {
                    // decrease the price of the item based on predefined allowed amount of variance
                    factor =
                            -1 * (random.nextInt(decreaseEventVar.second - decreaseEventVar.first)
                                    + decreaseEventVar.first)
                }
                 currentEvents.contains(item.increaseEvent) -> {
                    // increase the price based on event
                    factor =
                            1 * (random.nextInt(increaseEventVar.second
                                    - increaseEventVar.first)
                                    + decreaseEventVar.first)
            } else -> {
                    // determine if the variance increases the price
                    factor = if (random.nextBoolean()) 1 else -1
                    factor *= random.nextInt(item.variance)
                }
            }

            val netVariance = ((factor.toDouble()/100.0) * price).roundToInt()
            price += netVariance
        } else {
            price = -1
        }
        return price
    }

    override fun toString(): String {
        return toString(false)
    }

    /**
     * method to retrieve either a detailed or non-detailed version of the planet as a string
     *
     * @param detailed - whether you want a detailed to string
     *
     * @return the string representation of the planet
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
            builder.appendln(market)
        }
        return builder.toString()
    }

    override fun getEconomyName(): String {
        return name
    }

    companion object {
        // denote the variance effects from increase and decrease events
        val decreaseEventVar = Pair(40, 90)
        val increaseEventVar = Pair(50, 90)

        /**
         * creates planet object from xml node
         *
         * @param xmlNode - the xml node to create the planet from
         */
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

        /**
         * pulls text from specified child element of node
         *
         * @param xmlNode - the xml node
         * @param attr - the child element with the node to pull the text from
         *
         * @return the text from the attr in the xmlnode
         */
        private fun pullTextFromNode(xmlNode: Element, attr: String): String {
            return xmlNode.getElementsByTagName(attr).item(0).textContent
        }

        /**
         * forms a list of planets from a file
         *
         * @param fileInputStream - the input stream from the xml file containing the planets
         *
         * @return the list of planets within the xml file
         */
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
}