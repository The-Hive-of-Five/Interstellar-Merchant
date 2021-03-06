package com.cs2340.interstellarmerchant.model.universe.planet

import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent
import com.cs2340.interstellarmerchant.model.universe.market.Economy
import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech
import java.io.Serializable
import java.util.*
import java.util.stream.Collectors
import kotlin.math.roundToInt

/**
 * Used to determine various values for the planet's market based on attributes of the planet
 */
class PlanetEconomy(private val tech: Tech, private val resource: Resource,
                    private val currentEvents: Set<PlanetEvent>): Economy, Serializable {

    override val economyName: String
        get() = "Economy"

    override fun canBuyItem(item: Item, quantity: Int): OrderStatus {
        return (if (item.sellTechLevel > this.tech) {
                    OrderStatus.NOT_ENOUGH_TECH
                } else {
                    OrderStatus.SUCCESS
                })
    }


    /**
     * ONLY market should use (the class)
     */
    override fun filterItems(potentialItems: List<Item>): List<Item> {
        val random = Random()
        val chanceInStore = 80
        return potentialItems
                .stream()
                .filter { item: Item ->
                    var output = item.produceTechLevel <= this.tech
                    output = output && random.nextInt(100) < chanceInStore

                    return@filter output
                }
                .collect(Collectors.toList())
    }

    /**
     * ONLY market should use (the class)
     */
    override fun calculateQuantity(item: Item): Int {
        val random = Random()
        val factor: Int
        val signFactor: Int
        val minVariance: Int
        val maxVariance: Int
        if (item.idealTechLevel == this.tech) { // increase quantity if ideal tech level
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
     * Only market should use (the class)
     */
    override fun calculatePrice(item: Item): Int {
        var price: Int
        if (item != null) {
            price = item.base

            // account for tech level difference
            price += (this.tech.ordinal - item.sellTechLevel.ordinal) * item.priceIncreasePerTech

            val random = Random()
            val factor: Int
            when {
                item.decreaseResource == this.resource -> {
                    // decrease the price of the item based on predefined allowed amount of variance
                    factor =
                            -1 * (random.nextInt(Planet.DECREASE_EVENT_VARIANCE.second
                                    - Planet.DECREASE_EVENT_VARIANCE.first)
                                    + Planet.DECREASE_EVENT_VARIANCE.first)
                }
                currentEvents.contains(PlanetEvent(item.increaseEventType)) -> {
                    // increase the price based on event
                    factor =
                            1 * (random.nextInt(Planet.INCREASE_EVENT_VARIANCE.second
                                    - Planet.INCREASE_EVENT_VARIANCE.first)
                                    + Planet.INCREASE_EVENT_VARIANCE.first)
                } else -> {
                    // determine if the variance increases the price
                    val sign = if (random.nextBoolean()) 1 else -1
                    val variance = random.nextInt(item.variance)
                    factor = sign * variance
                }
            }

            val netVariance = ((factor.toDouble()/100.0) * price.toDouble()).toInt()
            price += netVariance
        } else {
            price = -1
        }
        return price
    }

}