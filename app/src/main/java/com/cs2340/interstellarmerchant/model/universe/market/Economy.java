package com.cs2340.interstellarmerchant.model.universe.market;

import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;

import java.util.List;

/**
 * Interface to be implemented by entities with economies (AKA traders and planets)
 * Economies DETERMINE the prices and quantities in the market
 */
public interface Economy {

    /**
     * @param order - the order of items the user is trying to sell
     * @return whether the order can proceed or not given the economy (can the economy
     * buy the items from the player)
     */
    default OrderStatus canBuyItems(Order order) {
        Item highestItem = order.getHighestSellTechItem();
        return canBuyItem(highestItem, order.getItemQuantity(highestItem));
    }

    /**
     * Can the host economy buy the item from the player
     * @param item - the item
     * @return whether the order can proceed or not (can the economy buy the items from the player)
     */
    OrderStatus canBuyItem(Item item, int quantity);

    /**
     * Calculates the price of the item based on the economy for the market
     * @param item - the item
     * @return - the item price
     */
    int calculatePrice(Item item);

    /**
     * Filters items from the potential items. Basically determines what items are sold
     * at the market
     * @param potentialItems - potential items within the economy
     * @return the items that will be in the market
     */
    List<Item> filterItems(List<Item> potentialItems);

    /**
     * Calculates the quantity for the item that should exist in the market
     * @param item - the item to calculate the quantity for
     * @return the quantity of the item
     */
    int calculateQuantity(Item item);

    /**
     * Gets the name of the economy
     * @return the economy name
     */
    String getEconomyName();

}
