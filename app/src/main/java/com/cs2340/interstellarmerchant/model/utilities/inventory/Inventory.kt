package com.cs2340.interstellarmerchant.model.utilities.inventory

import com.cs2340.interstellarmerchant.model.universe.market.items.Item

interface Inventory {
    /**
     * Whether the inventory contains the subset
     *
     * @param subset - whether the inventory contains the subset
     */
    operator fun contains(subset: Map<Item, Int>): Boolean

    /**
     * Contains for a single item
     *
     * @param item - the item
     * @param quantity - the quantity of the item
     * @return whether the item has the quantity or a greater quantity than the specified
     * quantity
     */
    fun contains(item: Item, quantity: Int): Boolean

    open operator fun plusAssign(subset: Map<Item, Int>)

    operator fun minusAssign(subset: Map<Item, Int>)
    /**
     * Gets the number of available space
     * @return the amount of available cargo
     */
    fun getAvailableSpace(): Int

    /**
     * Gets the item quantity. 0 if the quantity is not in the inventory
     *
     * @param item - the item
     *
     * @return the item quantity
     */
    fun getItemQuantity(item: Item): Int

    /**
     * @return get the amount of items in the inventory
     */
    fun getUsedSpace(): Int

    /**
     * Clear inventory
     */
    fun clearInventory()

    /**
     * gets items (clones the map so the player can't modify the inventory)
     *
     * @return the items in the inventory
     */
    fun getInventoryClone(): HashMap<Item, Int>
}