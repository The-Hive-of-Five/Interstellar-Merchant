package com.cs2340.interstellarmerchant.model.player.ship;

import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.utilities.inventory.StandardInventory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents ships
 */
public class Ship extends StandardInventory implements Serializable {
    private static final int STARTING_FUEL_AMOUNT = 500;

    private ShipType type;

    /**
     * Constructor for Ship class
     *
     * @param type - the type of the ship
     */
    public Ship(ShipType type) {
        super(type.cargoSpace);
        this.type  = type;
        giveStartingInventory();
    }

    /**
     * Gets the ship type
     * @return the ship type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * sets the ship type
     * @param type - the type of the ship
     */
    public void setType(ShipType type) {
        this.type = type;
    }

    private void giveStartingInventory() {
        Map<Item, Integer> startingInventory = new HashMap<>();
        startingInventory.put(Item.FUEL, STARTING_FUEL_AMOUNT);
        super.plusAssign(startingInventory);
    }
}
