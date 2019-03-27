package com.cs2340.interstellarmerchant.model.universe.planet

import com.cs2340.interstellarmerchant.model.GameController
import com.cs2340.interstellarmerchant.model.travel.Location
import com.cs2340.interstellarmerchant.model.universe.SolarSystem
import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent
import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEventType
import com.cs2340.interstellarmerchant.model.universe.market.Market
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech
import com.cs2340.interstellarmerchant.model.universe.time.TimeController
import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI
import com.cs2340.interstellarmerchant.model.utilities.AfterDeserialized
import org.w3c.dom.Element
import java.io.InputStream
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

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
                   var tech: Tech = Tech.getRandomTech(), var x: Int? = null, var y: Int? = null)
    : Location(), AfterDeserialized, Serializable, TimeSubscriberI {

    // keeps track of the current events (switch to event manager?)
    val currentEvents = HashSet<PlanetEvent>()

    // the planet's economy
    private val economy = PlanetEconomy(tech, resource, currentEvents)

    // market must be initialized after current events (market for the planet)
    val market = Market(economy)

    @Transient
    private var solarSystem: SolarSystem? = null

    init {
    }

    override fun afterDeserialized() {
        market.setEconomy(economy)
        market.afterDeserialized()
    }

    override fun dayUpdated(day: Int, controller: TimeController): Boolean {
        // run the event life cycle
        eventLifeCyle()

        // try to add a new event
        eventRoll(controller)

        return true
    }

    override fun onSubscribe(day: Int, timeController: TimeController) {
        for (event: PlanetEvent in currentEvents) {
            timeController.subscribeToTime(event)
        }
        eventRoll(timeController)
    }

    override fun onUnsubscribe(day: Int, controller: TimeController) {
        throw IllegalArgumentException("A planet should never onUnsubscribe from the " +
                "time controller")
    }

    override fun getX(): Int {
        return x!!
    }

    override fun getY(): Int {
        return y!!
    }

    fun setSolarSystem(solarSystem: SolarSystem) {
        this.solarSystem = solarSystem
    }

    override fun getSolarSystem(): SolarSystem {
        return this.solarSystem!!
    }

    override fun getLocationType(): LocationType {
        return LocationType.PLANET
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
        val builder = StringBuilder()
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

    /**
     * Removes events' with ended life cycles
     */
    private fun eventLifeCyle() {
        var eventDeleted = false
        val eventIterator = currentEvents.iterator()
        while (eventIterator.hasNext()) {
            val event = eventIterator.next()
            // remove expired events
            if (event.eventExpired()) {
                eventDeleted = true
                eventIterator.remove()
            }
        }

        // if an event was deleted, recalculate all market prices
        if (eventDeleted) {
            market.recalculatePrices()
        }

    }

    /**
     * Rolls the dice and adds an event if if dice within range
     */
    private fun eventRoll(controller: TimeController) {
        if (Random().nextInt(100) <= Planet.EVENT_CHANCE) {
            try {
                currentEvents.add(getRandomEvent())
                market.recalculatePrices()
            } catch (noEventException: NoEventException) {

            }
        }
    }

    class NoEventException(message: String): Exception(message)

    /**
     * Returns a random event based on the planet type. It will AUTOMATICALLY subscribe the
     * event to the time controller
     *
     * @return the planet event
     *
     * @throws NoSuchElementException if there are no possible event types
     */
    @Throws(NoEventException::class)
    private fun getRandomEvent(): PlanetEvent {
        var planetEventType: PlanetEventType? = PlanetEventType.getRandomPlanetEvent(this)
        planetEventType ?: throw NoEventException("There are no possible events")

        // subscribe the event to the time controller
        val event = PlanetEvent(planetEventType)
        val gameController: GameController = GameController.getInstance()
        val timeController: TimeController = gameController.timeController
        timeController.subscribeToTime(event)

        return event
    }

    /**
     * This is used to get the planets from the xml file.
     */
    companion object {
        // the chance of an event occurring the day starts
        private const val EVENT_CHANCE = 3

        // denote the variance effects from increase and decrease events
        val DECREASE_EVENT_VARIANCE = Pair(40, 90)
        val INCREASE_EVENT_VARIANCE = Pair(50, 90)

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
                    planets.add(createPlanetFromNode(
                            nList.item(nodeCounter) as Element))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return planets
        }
    }
}