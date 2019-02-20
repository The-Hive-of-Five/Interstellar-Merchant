package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.universe.Resource
import com.cs2340.interstellarmerchant.model.universe.Tech

/**
 * market class for planets
 *
 * @param hostTech - the tech of the hosting planet
 * @param hostResource - the resource of the hosting planet
 */
class Market(val hostTech: Tech, val hostResource: Resource) {
    val goods: List<MarketItem>

    init {
        val tempGoods: MutableList<MarketItem> = ArrayList(5)


        goods = tempGoods
    }
}