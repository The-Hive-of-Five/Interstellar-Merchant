package com.cs2340.interstellarmerchant.utilities

import com.cs2340.interstellarmerchant.model.universe.market.items.MarketItem

/**
 * An inventory class. Objects that extend this are given an inventory
 */
abstract class Inventory(val maxSize: Int = Inventory.DEFAULT_MAX){
    var size: Int = 0

    companion object {
        const val DEFAULT_MAX = 100000
    }

    // DO NOT MODIFY THIS FIELD OUTSIDE THIS CLASS
    private var inventory: MutableMap<MarketItem, Int>

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
            size += quantity
            if (inventory[item] != null) {
                inventory[item] = inventory[item]!! + quantity
            } else {
                inventory[item] = quantity
            }
        }
    }

    operator fun minusAssign(subset: Map<MarketItem, Int>) {
        for ((item: MarketItem, quantity: Int) in subset) {
            inventory[item] = inventory[item]!! - quantity
            size -= quantity
        }
    }

    /**
     * Gets the number of available space
     * @return the amount of available cargo
     */
    fun getAvailableSpace(): Int {
        return maxSize - size
    }

    /**
     * @return get the amount of items in the inventory
     */
    fun getUsedSpace(): Int {
        return size
    }

    /**
     * Clear inventory
     */
    fun clearInventory() {
        inventory = HashMap()
        size = 0
    }

    /**
     * gets items (clones the map so the player can't modify the inventory)
     *
     * @return the items in the inventory
     */
    fun getInventoryClone(): HashMap<MarketItem, Int> {
        return HashMap(inventory)
    }
}
