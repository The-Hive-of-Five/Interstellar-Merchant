package com.cs2340.interstellarmerchant.model.universe;

public enum Resource {
    NOSPECIALRESOURCES, MINERALRICH, MINERALPOOR, DESERT, LOTSOFWATER, RICHSOIL, POORSOIL,
    RICHFAUNA, LIFELESS, WEIRDMUSHROOMS, LOTSOFHERBS, ARTISTIC, WARLIKE;

    public static Resource getRandomResource() {
        Resource[] resources = Resource.values();
        return resources[(int) (Math.random() * resources.length)];
    }
}
