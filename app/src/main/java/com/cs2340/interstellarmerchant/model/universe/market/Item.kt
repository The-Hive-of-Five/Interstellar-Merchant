package com.cs2340.interstellarmerchant.model.universe.market

import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech

/**
 * The types of items that can be bought at marketplaces
 */
enum class Item (val produceTechLevel: Tech,
                 val sellTechLevel: Tech,
                 val idealTechLevel: Tech,
                 val base: Int,
                 val baseQuantity: Int,
                 val priceIncreasePerTech: Int,
                 val variance: Int,
                 val increaseEvent: PlanetEvent,
                 val decreaseResource: Resource,
                 val minTraderPrice: Int,
                 val maxTraderPrice: Int) {
    WATER(Tech.AGRICULTURE, Tech.AGRICULTURE, Tech.RENAISSANCE, 30, 10,
            3, 4, PlanetEvent.DROUGHT, Resource.LOTSOFWATER,
            30, 50),
    FURS(Tech.AGRICULTURE, Tech.AGRICULTURE, Tech.AGRICULTURE, 250, 10,
            10, 10, PlanetEvent.COLD, Resource.RICHFAUNA,
            230, 280),
    FOOD(Tech.MEDIEVAL, Tech.AGRICULTURE, Tech.MEDIEVAL, 100, 10,
            5,5, PlanetEvent.CROPFAIL, Resource.RICHSOIL,
            90, 160)
}
