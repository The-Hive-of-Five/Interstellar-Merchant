package com.cs2340.interstellarmerchant.model.travel;

import com.cs2340.interstellarmerchant.model.player.ship.Ship;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an entity that can travel the entity
 */
public abstract class TravelEntity {
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
}
