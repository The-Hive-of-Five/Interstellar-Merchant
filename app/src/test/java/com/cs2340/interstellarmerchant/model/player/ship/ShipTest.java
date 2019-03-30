package com.cs2340.interstellarmerchant.model.player.ship;

import com.cs2340.interstellarmerchant.model.universe.market.items.Item;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ShipTest {
    private static Ship ship;

    @BeforeClass
    public static void setup() {
        // instatiate the ship
        ship = new Ship(ShipType.GNAT);
    }

    @Before
    public void testSetup() {
        ship.clearInventory();
    }

    @Test
    public void noItemContainsTest() {
        assertFalse("The item should not be in the" +
                " in the ship's inventory", ship.contains(Item.FUEL, 5));
    }

    @Test
    public void notEnoughContainsTest() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        ship.plusAssign(addItem);
        assertFalse("The inventory does not contain enough of the item -- false",
                ship.contains(Item.FOOD, 6));
    }

    @Test
    public void enoughContainsTest() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        ship.plusAssign(addItem);
        assertTrue("The inventory contains enough of the item",
                ship.contains(Item.FOOD, 5));
    }
}
