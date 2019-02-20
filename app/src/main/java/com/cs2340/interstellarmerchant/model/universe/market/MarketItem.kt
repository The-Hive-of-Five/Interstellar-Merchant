package com.cs2340.interstellarmerchant.model.universe.market

import java.util.*

/**
 * Determines the appropriate price of the item based on the conditions
 *
 * @param item - the type of item
  *
 */
data class MarketItem(val item: Item, val economy: Economy) {
    val priceHistory: MutableList<Int> = LinkedList() // keeps track of the old prices
    var price: Int? = null

    init {

    }

    fun updatePrice(): Int? {
        if (price != null) {
            priceHistory.add(price!!) // throw exception if price is null
        }
        price = calculatePrice()
        return price
    }

    private fun calculatePrice(): Int {
        return economy.calculatePrice(item)
    }


}