package com.cs2340.interstellarmerchant.model.player.ship;

public class Ship {
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
