package com.cs2340.interstellarmerchant.utilities

import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import java.lang.IllegalArgumentException

/**
 * An inventory class. Objects that extend this are given an inventory
 */
abstract class Inventory(val maxSize: Int = Inventory.DEFAULT_MAX){
    var size: Int = 0 // accounts for the size of the item (AKA fuel has 0 size)
    var numberOfItems: Int = 0

    companion object {
        const val DEFAULT_MAX = 100000
    }

    // DO NOT MODIFY THIS FIELD OUTSIDE THIS CLASS
    private var inventory: MutableMap<Item, Int>

    init {
        inventory = HashMap()
    }

    /**
     * Whether the inventory contains the subset
     *
     * @param subset - whether the inventory contains the subset
     */
    operator fun contains(subset: Map<Item, Int>): Boolean {
        var valid = true
        for ((item: Item, quantity: Int) in subset) {
            if (inventory[item] == null || inventory[item]!! < quantity) {
                valid = false
            }
        }
        return valid
    }

    open operator fun plusAssign(subset: Map<Item, Int>) {
        var addSize: Int = 0
        subset.forEach{item: Item, quant: Int ->
            addSize += quant * item.sizeMultiplier
        }

        if (addSize > getAvailableSpace()) {
            throw IllegalArgumentException("The inventory is already full. By trying to add," +
                    "you are exceeding the inventory's capacity.")
        }

        for ((item: Item, quantity: Int) in subset) {
            numberOfItems += quantity
            size += quantity * item.sizeMultiplier
            if (inventory[item] != null) {
                inventory[item] = inventory[item]!! + quantity
            } else {
                inventory[item] = quantity
            }
        }
    }

    operator fun minusAssign(subset: Map<Item, Int>) {
        for ((item: Item, quantity: Int) in subset) {
            if (quantity > 0) {
                inventory[item] = inventory[item]!! - quantity
                size -= quantity * item.sizeMultiplier
                numberOfItems -= quantity

                if (inventory[item] == 0) {
                    inventory.remove(item)
                }
            }
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
     * Gets the item quantity. 0 if the quantity is not in the inventory
     *
     * @param item - the item
     *
     * @return the item quantity
     */
    fun getItemQuantity(item: Item): Int {
        return if (inventory[item] == null) 0 else inventory[item]!!
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
    fun getInventoryClone(): HashMap<Item, Int> {
        return HashMap(inventory)
    }
}
