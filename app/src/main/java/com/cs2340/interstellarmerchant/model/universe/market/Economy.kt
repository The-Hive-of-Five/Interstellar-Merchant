package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import com.cs2340.interstellarmerchant.model.universe.market.items.Order
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus

/**
 * Interface to be implemented by entities with economies (AKA traders and planets)
 * Economies DETERMINE the prices and quantities in the market
 */
interface Economy {

    /**
     * Gets the name of the economy
     * @return the economy name
     */
    open val economyName: String

    /**
     * @param order - the order of items the user is trying to sell
     * @return whether the order can proceed or not given the economy (can the economy
     * buy the items from the player)
     */
    fun canBuyItems(order: Order): OrderStatus {
        val highestItem = order.highestSellTechItem!!
        return canBuyItem(highestItem, order.getItemQuantity(highestItem))
    }

    /**
     * Can the host economy buy the item from the player
     * @param item - the item
     * @param quantity - the quantity of the item that the user is trying to sell to the market
     * @return whether the order can proceed or not (can the economy buy the items from the player)
     */
    open fun canBuyItem(item: Item, quantity: Int): OrderStatus

    /**
     * Calculates the price of the item based on the economy for the market
     * @param item - the item
     * @return - the item price
     */
    open fun calculatePrice(item: Item): Int

    /**
     * Filters items from the potential items. Basically determines what items are sold
     * at the market
     * @param potentialItems - potential items within the economy
     * @return the items that will be in the market
     */
    open fun filterItems(potentialItems: List<Item>): List<Item>

    /**
     * Calculates the quantity for the item that should exist in the market
     * @param item - the item to calculate the quantity for
     * @return the quantity of the item
     */
    open fun calculateQuantity(item: Item): Int

}
