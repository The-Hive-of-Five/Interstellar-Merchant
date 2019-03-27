package com.cs2340.interstellarmerchant.model.travel;

import android.util.Log;

import com.cs2340.interstellarmerchant.model.player.ship.Ship;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.utilities.inventory.Inventory;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents an entity that can travel the entity
 */
public abstract class TravelEntity implements Inventory, TravelController {
    private Location currentLocation;
    private final List<Location> locationHistory;

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
    public List<Location> getLocationHistory() {
        return locationHistory;
    }

    /**
     * Get's the entities ship
     * @return the ship
     */
    public abstract Ship getShip();

    /**
     * Sets the location
     * @param location - the new location (can't be null)
     */
    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("The new location can't be null");
        }
        if (currentLocation != null) {
            locationHistory.add(currentLocation);
        }
        currentLocation = location;
    }

    @Override
    public boolean contains(@NotNull Map<Item, Integer> subset) {
        Ship ship = getShip();
        return ship.contains(subset);
    }

    @Override
    public boolean contains(@NotNull Item item, int quantity) {
        Ship ship = getShip();
        return ship.contains(item, quantity);
    }

    @Override
    public void plusAssign(@NotNull Map<Item, Integer> subset) {
        Ship ship = getShip();
        ship.plusAssign(subset);
    }

    @Override
    public void minusAssign(@NotNull Map<Item, Integer> subset) {
        Ship ship = getShip();
        ship.minusAssign(subset);
    }

    @Override
    public int getAvailableSpace() {
        Ship ship = getShip();
        return ship.getAvailableSpace();
    }

    @Override
    public int getItemQuantity(@NotNull Item item) {
        Ship ship = getShip();
        return ship.getItemQuantity(item);
    }

    @Override
    public int getUsedSpace() {
        Ship ship = getShip();
        return ship.getUsedSpace();
    }

    @Override
    public void clearInventory() {
        Ship ship = getShip();
        ship.clearInventory();
    }

    @NotNull
    @Override
    public HashMap<Item, Integer> getInventoryClone() {
        Ship ship = getShip();
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

        Ship entityShip = this.getShip();
        Location returnLocation;

        TripLog tripLog = trip.getTripLog();
        if (entityShip.contains(Item.FUEL, tripLog.getFuelCost())) {
            returnLocation = definiteTravel(tripLog, trip.getEndingLocation(), timeController);
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
     * @param endingLocation - the ending location of the trip
     *
     * @return the new location AKA the one stored in the trip param
     */
    private Location definiteTravel(TripLog tripLog, Location endingLocation, TimeController
                                    timeController) {
        Ship entityShip = this.getShip();

        // remove the fuel
        Map<Item, Integer> removeMap = new HashMap<>();
        removeMap.put(Item.FUEL, tripLog.getFuelCost());
        this.minusAssign(removeMap);

        // time jump by the amount of time the trip takes
        timeController.timeJump(tripLog.getTime());

        // travel to the location
        this.setLocation(endingLocation);

        return endingLocation;
    }
}
