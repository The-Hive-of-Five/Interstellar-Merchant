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

    init {
        val acceptableItems = hostEconomy.filterItems(Item.values().toList())
        for (item: Item in acceptableItems) {
            // add the item to the store
            inventory[MarketItem(item, hostEconomy)] = hostEconomy.calculateQuantity(item)
        }
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
            var output: OrderStatus = player.canBuyItems(order)
            if (output == OrderStatus.SUCCESS) {
                // if the player can actually buy the items, proceed with the transaction

                // remove the items from the current inventory
                this -= order.order

                // remove credits from player
                player.credits = player.credits - order.totalCost

                // add them to the player's ship
                player.ship += order.order
            }
            return output
        }
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

                // add the items to the market's inventory
                this += order.order

                // remove them from the player's ship
                player.ship -= order.order

                // add credits to player
                player.credits = player.credits + order.totalCost

            }
            return output
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("Store for ${hostEconomy.economyName}")
        for ((item: MarketItem, quantity: Int) in inventory) {
            builder.appendln("$item with quantity, $quantity")
        }
        return builder.toString()
    }
}