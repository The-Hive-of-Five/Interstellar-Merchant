package com.cs2340.interstellarmerchant.model.travel;

import android.util.Log;

import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.ship.Ship;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;

import java.util.HashMap;
import java.util.Map;

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
        if (trip.getFuelCost() <=  entityShip.getItemQuantity(Item.FUEL)) {
            // remove
            Map<Item, Integer> removeMap = new HashMap<>();
            removeMap.put(Item.FUEL, trip.getFuelCost());
            entityShip.minusAssign(removeMap);

            // travel to the location
            entity.setLocation(newLocation);
            returnLocation = newLocation;
        } else {
            // don't travel because not enough fuel
            returnLocation = entity.getCurrentLocation();
        }
        Log.d("TRAVEL",returnLocation.toString());
        return returnLocation;
    }

    /**
     * Assumes the entity that is travelling has enough fuel. WILL definitely go to the input
     * location. PERFORMS necessary FUEL REMOVAL and TIME JUMP
     * @param entity - the entity travelling
     * @param trip - the trip
     *
     * @return the new location AKA the one stored in the trip param
     */
    private Location definiteTravel(TravelEntity entity, Trip trip) {
        Ship entityShip = entity.getShip();

        // remove the fuel
        Map<Item, Integer> removeMap = new HashMap<>();
        removeMap.put(Item.FUEL, trip.getFuelCost());
        entityShip.minusAssign(removeMap);

        // time jump by the amount of time the trip takes
        GameController gameController = GameController.getInstance();
        TimeController timeController = gameController.getTimeController();
        timeController.timeJump(trip.getTime());

        // travel to the location
        entity.setLocation(trip.getEndingLocation());

        return trip.getEndingLocation();
    }
}


