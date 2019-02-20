package com.cs2340.interstellarmerchant.model.universe;

/**
 * Keeps track of the various tech levels
 */
public enum Tech implements Comparable<Tech>{
    AGRICULTURE, MEDIEVAL, RENAISSANCE, EARLY_INDUSTRIAL, INDUSTRIAL, POST_INDUSTRIAL, HI_TECH;

    /**
     * Gets a random tech level
     * @return the random tech level
     */
    public static Tech getRandomTech() {
        Tech[] tech = Tech.values();
        return tech[(int) (Math.random() * tech.length)];
    }
    
}
