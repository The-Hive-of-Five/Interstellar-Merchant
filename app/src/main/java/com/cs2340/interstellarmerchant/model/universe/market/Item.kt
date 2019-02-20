package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.universe.Resource
import com.cs2340.interstellarmerchant.model.universe.Tech
import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent

/**
 * The types of items that can be bought at marketplaces
 */
enum class Item (val produceTechLevel: Tech,
                 val sellTechLevel: Tech,
                 val idealTechLevel: Tech,
                 val base: Int,
                 val priceIncreasePerTech: Int,
                 val variance: Int,
                 val increaseEvent: PlanetEvent,
                 val decreaseResource: Resource,
                 val minTraderPrice: Int,
                 val maxTraderPrice: Int)
