package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.player.Player
import com.cs2340.interstellarmerchant.model.universe.market.items.Item
import com.cs2340.interstellarmerchant.model.universe.market.items.MarketItem
import com.cs2340.interstellarmerchant.model.universe.market.items.Order
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus
import com.cs2340.interstellarmerchant.utilities.Inventory
import java.io.Serializable


/**
 * market class for planets
 *
 * @param hostEconomy - the economy of the hosting planet
 */
class Market(private val hostEconomy: Economy): Inventory( ), Serializable {

    private var priceLog: MutableMap<Item, MarketItem>

    init {
        priceLog = HashMap() // initialize price map

        val acceptableItems = hostEconomy.filterItems(Item.values().toList())
        val itemsMap: MutableMap<Item, Int> = HashMap()
        for (item: Item in acceptableItems) {
            // add the item to the store inventory
            itemsMap[item] = hostEconomy.calculateQuantity(item)
            // add the item to the store price reference
            addItemToPriceRef(item)
        }
        // add the items to the inventory
        super.plusAssign(itemsMap)
    }

    override operator fun plusAssign(subset: Map<Item, Int>) {
        super.plusAssign(subset)
        for ((item: Item) in subset) { // if the item does not have a price record, add it
            addItemToPriceRef(item)
        }
    }

    /**
     * Conditionally adds item to price log AKA dictionary of prices that the market can reference
     *
     * @param item - the item
     */
    fun addItemToPriceRef(item: Item): Int {
        if (priceLog[item] == null) {
            priceLog[item] = MarketItem(item, hostEconomy)
        }
        return priceLog[item]!!.price!!
    }

    /**
     * Used to buy items
     *
     * @param order - the player's orders
     * @param player - the player buying the items
     */
    fun buyItems(order: Order, player: Player): OrderStatus {
        if(!super.contains(order.order)) { // make sure the market actually has the items
            throw IllegalArgumentException("The order you gave was not valid")
        } else {
            calculateOrderPrice(order) // sets the price attribute of order
            var output: OrderStatus = player.canBuyItems(order)
            if (output == OrderStatus.SUCCESS) {
                // if the player can actually buy the items, proceed with the transaction

                // remove the items from the current inventory
                this -= order.order

                // remove credits from player
                player.credits = player.credits - order.getTotalCost()

                // add them to the player's ship
                player.ship += order.order
            }
            return output
        }
    }

    /**
     * Calculates the order's price based on the market and updates its price attribute
     *
     * @param order - the order
     */
    fun calculateOrderPrice(order: Order) {
        var totalCost: Int = 0
        for ((item: Item, quantity: Int) in order.order) {
            totalCost += priceLog[item]!!.price!! * quantity
        }
        order.setPrice(totalCost)
    }

    /**
     * Gets the item buy price (aka user wants to buy the item)
     * If the item is not in the store, a null pointer error will be thrown
     *
     * @param item - the item being bought
     */
    fun getItemBuyPrice(item: Item): Int {
        return priceLog[item]!!.price!!
    }

    /**
     * Gets the sell price of the item (value the host economy will pay the player for the item)
     *
     * @param item - the item you're trying to get the sell price of
     * @return the item price or -1 if the host economy can't buy the item
     */
    fun getItemSellPrice(item: Item): Int {
        var output: Int
        val order = HashMap<Item, Int>()
        if (hostEconomy.canBuyItem(item, 1) == OrderStatus.SUCCESS) {
            // calculates market price for item if not in price log (.8 to reduce sell price)
            output = (addItemToPriceRef(item) * .8).toInt()

        } else {
            // means the host economy can't even buy this item from the player
            output = -1
        }
        return output
    }

    /**
     * Let's the market buy items from the player (the player sells items)
     *
     * @param order - the player's orders
     * @param player - the player buying the items
     */
    fun sellItems(order: Order, player: Player): OrderStatus {
        val playerInventory: Inventory = player.ship
        if(!playerInventory.contains(order.order)) { // make sure the player actually has the items
            throw IllegalArgumentException("The order you gave was not valid")
        } else {
            var output: OrderStatus = hostEconomy.canBuyItems(order)
            if (output == OrderStatus.SUCCESS) {
                /* if the market can actually buy the items from the player,
                 proceed with the transaction */

                /* add the items to the market's inventory
                 the plus assign function will also assign new items not in the shop a  value
                 */
                this += order.order
                calculateOrderPrice(order) // gives the order a price

                // remove them from the player's ship
                player.ship -= order.order

                // add credits to player
                player.credits = player.credits + order.getTotalCost()

            }
            return output
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("Store for ${hostEconomy.economyName}")
        for ((item: Item, quantity: Int) in super.getInventoryClone()) {
            builder.appendln("${priceLog[item]} with quantity, $quantity")
        }
        return builder.toString()
    }
}