package com.cs2340.interstellarmerchant.model.universe.market

import java.io.Serializable
import java.util.*

/**
 * Determines the appropriate price of the item based on the conditions
 *
 * @param item - the type of item
  *
 */
data class MarketItem(private val item: Item, private val economy: Economy): Serializable {
    val priceHistory: MutableList<Int> = LinkedList() // keeps track of the old prices
    var price: Int? = null

    init {
        // calculate the initial price
        price = economy.calculatePrice(item)
    }

    fun updatePrice(): Int? {
        if (price != null) {
            priceHistory.add(price!!) // throw exception if price is null
        }
        price = calculatePrice()
        return price
    }

    override fun toString(): String {
        return "${item.name} - $price: "
    }

    private fun calculatePrice(): Int {
        return economy.calculatePrice(item)
    }


}