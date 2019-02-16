package com.cs2340.interstellarmerchant.model.player.ship;

import java.io.Serializable;

public class Ship implements Serializable {
    private ShipType type;

    public Ship(ShipType type) {
        this.type  = type;
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }
}
