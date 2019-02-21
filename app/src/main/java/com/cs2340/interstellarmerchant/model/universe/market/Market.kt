package com.cs2340.interstellarmerchant.model.universe.market

import java.io.Serializable


/**
 * market class for planets
 *
 * @param hostTech - the tech of the hosting planet
 * @param hostResource - the resource of the hosting planet
 */
class Market(private val hostEconomy: Economy): Serializable {
    val goods: MutableMap<MarketItem, Int>

    init {
        goods = HashMap()
        val acceptableItems = hostEconomy.filterItems(Item.values().toList())
        for (item: Item in acceptableItems) {
            // add the item to the store
            goods[MarketItem(item, hostEconomy)] = hostEconomy.calculateQuantity(item)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("Store for ${hostEconomy.economyName}")
        for ((item: MarketItem, quantity: Int) in goods) {
            builder.appendln("$item with quantity, $quantity")
        }
        return builder.toString()
    }
}