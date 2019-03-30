package com.cs2340.interstellarmerchant.model.travel;

import com.cs2340.interstellarmerchant.model.universe.SolarSystem;

/**
 * Abstract class that represents a location that can be travelled to
 *
 * SUPRRESSED because they are used in Kotlin, which the checkstyle does not account for (in trip)
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class Location {
    /**
     * gets the x value of the location
     * @return the x value
     */
    public abstract int getX();

    /**
     * gets the y location
     * @return the y value
     */
    public abstract int getY();

    /**
     * The types of locations. Can be used by GUI to determine what screen to go to
     */
    public enum LocationType {
        PLANET, TRADER, PIRATE_ATTACK
    }

    /**
     * Gets the location type so the GUI can determine how to display something
     * @return the location type
     */
    public abstract LocationType getLocationType();

    /**
     * Gets the solar system
     * @return the solar system
     */
    public abstract SolarSystem getSolarSystem();
}
