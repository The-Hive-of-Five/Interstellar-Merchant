package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.universe.Resource

/**
 * Determines the appropriate price of the item based on the conditions
 *
 * @param item - the type of item
 * @param resource - the type of resource of the planet
 *
 */
data class MarketItem(val item: Item, val resource: Resource) {
    init {

    }
}