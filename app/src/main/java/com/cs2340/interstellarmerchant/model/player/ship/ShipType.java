package com.cs2340.interstellarmerchant.model.player.ship;

/**
 * enum class to represent ship types
 */
public enum ShipType {
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

    /**
     * constructor for ShipType
     * @param cargoSpace - the cargo space of the ship
     */
    ShipType(int cargoSpace) {
        this.cargoSpace = cargoSpace;
    }
}
