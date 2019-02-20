package com.cs2340.interstellarmerchant.model.player.ship;

import com.cs2340.interstellarmerchant.model.universe.market.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents ships
 */
public class Ship implements Serializable {
    private ShipType type;
    private List<Item> cargo;

    /**
     * Constructor for Ship class
     *
     * @param type - the type of the ship
     */
    public Ship(ShipType type) {
        this.type  = type;
        cargo = new ArrayList<>(type.cargoSpace);
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
