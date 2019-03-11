package com.cs2340.interstellarmerchant.model.travel;

import com.cs2340.interstellarmerchant.model.player.ship.Ship;

import javax.inject.Singleton;

/**
 * Controls travel for all travel entities
 * A Singleton
 */
@Singleton
public class TravelController {
    private static TravelController controller;

    /**
     * Gets the instance of travel controller
     * @return the travel controller
     */
    public static TravelController getInstance() {
        if (controller == null) {
            controller = new TravelController();
        }
        return controller;
    }

    /**
     * Set's the travel entity's location without penalty (teleports)
     *
     * @param entity - the travel entity
     * @param newLocation - the new location
     *
     */
    public void setLocationWithoutPenalty(TravelEntity entity, Location newLocation) {
        entity.setLocation(newLocation);

    }

    /**
     * Set's the travel entity's location with penalty. Possibility of a random
     * Random events could take you to a DIFFERENT LOCATION
     *
     * @param entity - the travel entity
     * @param newLocation - the new location
     *
     * @return the ACTUAL location the ship travels to*
     */
    public Location Travel(TravelEntity entity, Location newLocation) {
        Trip trip = new Trip(entity.getCurrentLocation(), newLocation);
        Ship entityShip = entity.getShip();

        Location returnLocation;
        if (trip.getFuelCost() <  5) {
            // the ship can't afford to travel
            returnLocation = entity.getCurrentLocation();
        } else {
            // use the ship's fuel to travel to the location
            returnLocation = newLocation;
        }

        return returnLocation;
    }
}
