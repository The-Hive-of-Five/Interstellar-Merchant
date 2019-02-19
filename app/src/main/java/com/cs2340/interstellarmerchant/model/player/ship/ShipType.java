package com.cs2340.interstellarmerchant.model.player.ship;

import java.io.Serializable;

public enum ShipType implements Serializable {
    FLEA(5),
    GNAT(15),
    FIREFLY(20),
    MOSQUITO(15),
    BUMBLEBEE(20) ,
    BEETLE(50),
    HORNET(20),
    GRASSHOPPER(30),
    TERMITE(60),
    WASP(60);

    public final int cargoSpace;

    ShipType(int cargoSpace) {
        this.cargoSpace = cargoSpace;
    }
}
