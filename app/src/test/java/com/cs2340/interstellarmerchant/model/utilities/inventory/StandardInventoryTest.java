package com.cs2340.interstellarmerchant.model.utilities.inventory;

import com.cs2340.interstellarmerchant.model.universe.market.items.Item;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("ALL")
public class StandardInventoryTest {
    private static Inventory inventory;

    @BeforeClass
    public static void setup() {
        // instatiate the ship
        inventory = new StandardInventory(15);
    }

    @Before
    public void testSetup() {
        inventory.clearInventory();
    }

    @Test
    public void noItemContainsTest() {
        assertFalse("The item should not be in the" +
                " in the ship's inventory", inventory.contains(Item.FUEL, 5));
    }

    @Test
    public void notEnoughContainsTest() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        inventory.plusAssign(addItem);
        assertFalse("The inventory does not contain enough of the item -- false",
                inventory.contains(Item.FOOD, 6));
    }

    @Test
    public void enoughContainsTest() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        inventory.plusAssign(addItem);
        assertTrue("The inventory contains enough of the item",
                inventory.contains(Item.FOOD, 5));
    }

    @Test
    public void orderContainsTest() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        inventory.plusAssign(addItem);
        assertTrue("The inventory contains enough of the item",
                inventory.contains(addItem));
    }
    @Test
    public void orderContainsTestNotEnough() {
        Map<Item, Integer> addItem = new HashMap<>();
        addItem.put(Item.FOOD, 5);
        inventory.plusAssign(addItem);
        addItem.put(Item.FOOD, 6);
        assertTrue("The inventory does not contain enough of the item",
                !inventory.contains(addItem));
    }
}
