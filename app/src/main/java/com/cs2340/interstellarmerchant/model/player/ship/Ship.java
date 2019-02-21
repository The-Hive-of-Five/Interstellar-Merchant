package com.cs2340.interstellarmerchant.model.player.ship;

import com.cs2340.interstellarmerchant.utilities.Inventory;

import java.io.Serializable;

/**
 * Class that represents ships
 */
public class Ship extends Inventory implements Serializable {
    private ShipType type;

    /**
     * Constructor for Ship class
     *
     * @param type - the type of the ship
     */
    public Ship(ShipType type) {
        super(type.cargoSpace);
        this.type  = type;
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
}
