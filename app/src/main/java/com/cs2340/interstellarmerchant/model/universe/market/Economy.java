package com.cs2340.interstellarmerchant.model.universe.market;

import java.util.List;

/**
 * Interface to be implemented by entities with economies (AKA traders and planets)
 */
public interface Economy {
    /**
     * Calculates the price of the item based on the economy
     * @param item - the item
     * @return - the item price
     */
    int calculatePrice(Item item);

    /**
     * Filters items from the potential items. Basically determines what items are sold
     * at the market
     * @return the items that will be in the market
     */
    List<Item> filterItems(List<Item> potentialItems);

    /**
     * Calculates the quantity for the item
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
