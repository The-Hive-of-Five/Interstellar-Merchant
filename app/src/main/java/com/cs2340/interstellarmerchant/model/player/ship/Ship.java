package com.cs2340.interstellarmerchant.model.player.ship;

import com.cs2340.interstellarmerchant.model.universe.market.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {
    private ShipType type;
    private List<Item> cargo;

    public Ship(ShipType type) {
        this.type  = type;
        cargo = new ArrayList<>(type.cargoSpace);
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }
}
