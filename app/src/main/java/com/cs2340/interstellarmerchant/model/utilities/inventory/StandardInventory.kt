package com.cs2340.interstellarmerchant.model.utilities.inventory

import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import java.lang.IllegalArgumentException

/**
 * An inventory class. Objects that extend this are given an inventory
 */
abstract class StandardInventory(val maxSize: Int = DEFAULT_MAX) : Inventory {
    var size: Int = 0 // accounts for the size of the item (AKA fuel has 0 size)
    private var numberOfItems: Int = 0

    companion object {
        const val DEFAULT_MAX = 100000
    }

    // DO NOT MODIFY THIS FIELD OUTSIDE THIS CLASS
    private var inventory: MutableMap<Item, Int>

    init {
        inventory = HashMap()
    }

    override operator fun contains(subset: Map<Item, Int>): Boolean {
        var valid = true
        for ((item: Item, quantity: Int) in subset) {
            valid = contains(item, quantity)
            if (!valid) {
                break
            }
        }
        return valid
    }

    override fun contains(item: Item, quantity: Int): Boolean {
        var contains = true
        if (inventory[item] == null || inventory[item]!! < quantity) {
            contains = false
        }
        return contains
    }

    override operator fun plusAssign(subset: Map<Item, Int>) {
        var addSize = 0
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

    override operator fun minusAssign(subset: Map<Item, Int>) {
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

    override fun getAvailableSpace(): Int {
        return maxSize - size
    }

    override fun getItemQuantity(item: Item): Int {
        return if (inventory[item] == null) 0 else inventory[item]!!
    }

    override fun getUsedSpace(): Int {
        return size
    }

    override fun clearInventory() {
        inventory = HashMap()
        size = 0
    }

    override fun getInventoryClone(): HashMap<Item, Int> {
        return HashMap(inventory)
    }
}
