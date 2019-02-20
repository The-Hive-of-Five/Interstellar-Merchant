package com.cs2340.interstellarmerchant.model.universe.market;

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
}
