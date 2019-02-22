package com.cs2340.interstellarmerchant.model.universe.market.items

import com.cs2340.interstellarmerchant.model.universe.market.Economy
import java.io.Serializable
import java.util.*

/**
 * Determines the appropriate price of the item based on the conditions
 *
 * @param item - the type of item
  *
 */
data class MarketItem(val item: Item, private val economy: Economy): Serializable {
    companion object {
        const val sellPriceValue = .8
    }

    val priceHistory: MutableList<Int> = LinkedList() // keeps track of the old prices
    val sellPriceHistory: MutableList<Int> = LinkedList() // keeps track of the sell price
    var price: Int? = null
    var sellPrice: Int? = null // the price the store is willing to pay for the item

    init {
        // calculate the initial price
        price = economy.calculatePrice(item)
    }

    /**
     * Updates price and sell price
     *
     * @return the sell price
     */
    fun updatePrice(): Int? {
        if (price != null) {
            // if price is not null, then sell price is not null
            priceHistory.add(price!!) // throw exception if price is null
            sellPriceHistory.add(sellPrice!!) // throw exception if sell price is null
        }
        price = calculatePrice()
        sellPrice = calculateSellPrice(price)
        return price
    }

    override fun toString(): String {
        return "${item.name} - $price: "
    }

    private fun calculatePrice(): Int {
        return economy.calculatePrice(item)
    }

    private fun calculateSellPrice(buyPrice: Int?): Int {
        return (buyPrice!! * sellPriceValue).toInt()
    }


}