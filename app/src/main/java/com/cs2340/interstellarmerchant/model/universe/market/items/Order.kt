package com.cs2340.interstellarmerchant.model.universe.market.items

import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech
import java.io.Serializable

/**
 * Order wrapper
 */
data class Order(val order: Map<Item, Int>): Serializable {
    private var totalCost: Int? = null
    var quantity = 0
    // the minimum technology an entity needs to be able to sell all the items in the order
    var minSellTech: Tech? = null
    init {
        for ((item: Item, quantity: Int) in order) {
            if (minSellTech == null || minSellTech!! < item.sellTechLevel) {
                minSellTech = item.sellTechLevel
            }
            this.quantity += quantity
        }
    }

    /**
     * Updates the cost of the order to the market's current prices
     *
     * @param totalCost - the cost of all the items as determined by the market
     */
    fun setPrice(totalCost: Int) {
        this.totalCost = totalCost;
    }

    /**
     * Gets the total cost of the order
     * @return the total cost
     */
    fun getTotalCost(): Int {
        if (totalCost == null) {
            throw IllegalStateException("No market has been passed to the order, so the order's" +
                    "actual value hasn't been calculated");
        } else {
            return totalCost!!;
        }
    }



}

/**
 * Denotes the status of an order
 */
enum class OrderStatus {
    NOT_ENOUGH_SPACE,
    NOT_ENOUGH_CREDITS,
    NOT_ENOUGH_TECH,
    SUCCESS
}