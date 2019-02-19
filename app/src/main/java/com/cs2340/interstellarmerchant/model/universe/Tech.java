package com.cs2340.interstellarmerchant.model.universe;

import java.io.Serializable;

public enum Tech implements Serializable {
    AGRICULTURE, MEDIEVAL, RENAISSANCE, EARLY_INDUSTRIAL, INDUSTRIAL, POST_INDUSTRIAL, HI_TECH;

    public static Tech getRandomTech() {
        Tech[] tech = Tech.values();
        return tech[(int) (Math.random() * tech.length)];
    }
}
