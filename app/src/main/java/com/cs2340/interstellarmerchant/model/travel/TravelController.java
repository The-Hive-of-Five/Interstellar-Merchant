package com.cs2340.interstellarmerchant.model.travel;

import com.cs2340.interstellarmerchant.model.universe.time.TimeController;

/**
 * Basically a temporary interface so I didn't have to refactor all the code and replace
 * it with TravelEntity
 */
public interface TravelController {
    /**
     * Set's the travel entity's location without penalty (teleports)
     *
     * @param newLocation - the new location
     *
     */
    void setLocationWithoutPenalty(Location newLocation);


    /**
     * Set's the travel entity's location with penalty. Possibility of a random
     * Random events could take you to a DIFFERENT LOCATION
     *
     * @param newLocation - the new location
     * @param timeController - the time controller
     *
     * @return the ACTUAL location the ship travels to*
     */
    Location travel(Location newLocation, TimeController timeController);


}
