package com.cs2340.interstellarmerchant.model.travel;

import android.util.Log;

import com.cs2340.interstellarmerchant.model.player.ship.Ship;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.utilities.AfterDeserialized;
import com.cs2340.interstellarmerchant.model.utilities.inventory.Inventory;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Represents an entity that can travel the entity
 */
public abstract class TravelEntity implements AfterDeserialized, Inventory, Serializable,
        TravelController {
    protected Ship ship;

    private transient Location currentLocation;

    private LocationOverview locationOverview;

    /**
     * Should be called BEFORE after deserialized
     * @param universe - the universe of the location entity
     */
    public void afterDeserializedSpecialized(Universe universe) {
        currentLocation = universe.getPlanetFromSolarSystem(locationOverview.getPlanetName(),
                locationOverview.getSolarSystem());
    }

    @Override
    public void afterDeserialized() {
        if (currentLocation == null) {
            throw new IllegalStateException("The current location wasn't correctly deserialized");
        }
    }

    private final List<LocationOverview> locationHistory;

    /**
     * Constructor for the TravelEntity
     */
    protected TravelEntity() {
        locationHistory = new LinkedList<>();
    }

    /**
     * Gets current location
     * @return the entity's current location
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Gets the location history
     * @return the entity's location history
     */
    public List<LocationOverview> getLocationHistory() {
        return Collections.unmodifiableList(locationHistory);
    }

    /**
     * Get's the entities ship
     * @return the ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets the location
     * @param location - the new location (can't be null)
     */
    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("The new location can't be null");
        }

        locationOverview = ((Planet) location).getLocationOverview();

        if (currentLocation != null) {
            locationHistory.add(locationOverview);
        }
        currentLocation = location;

    }

    @Override
    public boolean contains(@NotNull Map<Item, Integer> subset) {
        return ship.contains(subset);
    }

    @Override
    public boolean contains(@NotNull Item item, int quantity) {
        return ship.contains(item, quantity);
    }

    @Override
    public void plusAssign(@NotNull Map<Item, Integer> subset) {
        ship.plusAssign(subset);
    }

    @SuppressWarnings("WeakerAccess")
    @Override
    public void minusAssign(@NotNull Map<Item, Integer> subset) {
        ship.minusAssign(subset);
    }

    @Override
    public int getAvailableSpace() {
        return ship.getAvailableSpace();
    }

    @Override
    public int getItemQuantity(@NotNull Item item) {
        return ship.getItemQuantity(item);
    }

    @Override
    public int getUsedSpace() {
        return ship.getUsedSpace();
    }

    @Override
    public void clearInventory() {
        ship.clearInventory();
    }

    @NotNull
    @Override
    public HashMap<Item, Integer> getInventoryClone() {
        return ship.getInventoryClone();
    }

    @Override
    public void setLocationWithoutPenalty(Location newLocation) {
        this.setLocation(newLocation);
    }

    @Override
    public Location travel(Location newLocation, TimeController timeController) {
        Location currentLocation = this.getCurrentLocation();
        Trip trip = new Trip(currentLocation, newLocation);

        int fuelCost = trip.getFuelCost();

        Location returnLocation;
        if (ship.contains(Item.FUEL, fuelCost)) {
            returnLocation = definiteTravel(trip.getTripLog(), fuelCost,
                    timeController);
        } else {
            // don't travel because not enough fuel
            returnLocation = currentLocation;
        }
        Log.d("TRAVEL",returnLocation.toString());
        return returnLocation;
    }

    /**
     * Assumes the entity that is travelling has enough fuel. WILL definitely go to the input
     * location. PERFORMS necessary FUEL REMOVAL and TIME JUMP
     * @param tripLog - the trip log
     *
     * @return the new location AKA the one stored in the trip param
     */
    private Location definiteTravel(TripLog tripLog, int fuelCost, TimeController
                                    timeController) {
        // remove the fuel
        Map<Item, Integer> removeMap = new HashMap<>();
        removeMap.put(Item.FUEL, fuelCost);
        this.minusAssign(removeMap);

        // time jump by the amount of time the trip takes
        timeController.timeJump(tripLog.getTime());

        Location endingLocation = tripLog.getEndingLocation();

        // travel to the location
        this.setLocation(endingLocation);

        return endingLocation;
    }
}
