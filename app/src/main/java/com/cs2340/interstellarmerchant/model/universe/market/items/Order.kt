package com.cs2340.interstellarmerchant.model.universe.market.items

import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech

/**
 * Order wrapper
 */
data class Order(val order: Map<MarketItem, Int>) {
    var totalCost = 0
    var quantity = 0
    // the minimum technology an entity needs to be able to sell all the items in an order
    var minSellTech: Tech? = null
    init {
        for ((item: MarketItem, quantity: Int) in order) {
            if (minSellTech == null || minSellTech!! < item.item.sellTechLevel) {
                minSellTech = item.item.sellTechLevel
            }
            totalCost += item.price!! * quantity
            this.quantity += quantity
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