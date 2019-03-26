package com.cs2340.interstellarmerchant.model.universe.planet;

import com.cs2340.interstellarmerchant.model.universe.market.Economy;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource;
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class PlanetEconomyTest {
    private static List<Economy> economies;

    @BeforeClass
    public static void testInitialize() {
        economies = new LinkedList<>();

        Resource[] resources = Resource.values();
        Tech[] techTypes = Tech.values();
        for (Resource resource: resources) {
            for (Tech tech: techTypes) {
                economies.add(new PlanetEconomy(tech, resource, new LinkedHashSet<>()));
            }
        }
    }
    @Test
    public void fuelPriceTest() {
        for (Economy economy: economies) {
            if (economy.canBuyItem(Item.FUEL, 1) == OrderStatus.SUCCESS) {
                int numberOfGenerations = 1000;
                Map<Item, Integer> fuelInv = new HashMap<>();
                while (numberOfGenerations > 0) {
                    int fuelPrice = economy.calculatePrice(Item.FUEL);
                    assertThat("Fuel price is greater than 0", fuelPrice > 0);
                    numberOfGenerations--;
                }
            }
        }

    }
}
