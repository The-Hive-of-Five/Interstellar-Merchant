package com.cs2340.interstellarmerchant.utilities

import com.cs2340.interstellarmerchant.model.universe.market.items.MarketItem

/**
 * An inventory class. Objects that extend this are given an inventory
 */
abstract class Inventory(val maxSize: Int = Inventory.DEFAULT_MAX){
    companion object {
        const val DEFAULT_MAX = 100000
    }
    @JvmField protected var inventory: MutableMap<MarketItem, Int>

    init {
        inventory = HashMap()
    }

    /**
     * Whether the inventory contains the subset
     *
     * @param subset - whether the inventory contains the subset
     */
    operator fun contains(subset: Map<MarketItem, Int>): Boolean {
        var valid = true
        for ((item: MarketItem, quantity: Int) in subset) {
            if (subset[item] == null || subset.getValue(item) < quantity) {
                valid = false
            }
        }
        return valid
    }

    operator fun plusAssign(subset: Map<MarketItem, Int>) {
        for ((item: MarketItem, quantity: Int) in subset) {
            inventory[item] = inventory[item]!! + quantity
        }
    }

    operator fun minusAssign(subset: Map<MarketItem, Int>) {
        for ((item: MarketItem, quantity: Int) in subset) {
            inventory[item] = inventory[item]!! - quantity
        }
    }

    /**
     * Gets the number of available space
     * @return the amount of available cargo
     */
    fun getAvailableSpace(): Int {
        return maxSize - inventory.size
    }
}
