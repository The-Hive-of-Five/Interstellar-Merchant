package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Enum to represent events on planets
 */
public enum PlanetEvent {
    DROUGHT(new Resource[] {Resource.LOTSOFWATER}),
    COLD,
    CROPFAIL(new Resource[] {Resource.RICHSOIL}),
    WAR,
    BOREDOM,
    PLAGUE,
    LACKOFWORKERS;

    private final Set<Resource> conflictingResources;

    /**
     * constructor for planet event
     * @param conflictingResources - the resources that would conflict with this event.
     *                             AKA if the planet has the resource, the event cannot
     *                             occurr
     */
    PlanetEvent(Resource[] conflictingResources) {
        this.conflictingResources = new HashSet<>(Arrays.asList(conflictingResources));
    }

    PlanetEvent() {
        this(new Resource[0]);
    }

    /**
     * Gets a random event for the planet. Ensures the event does not conflict with the
     * planet's resources
     * @param planet - the input planet
     * @return the random event
     */
    public static PlanetEvent getRandomPlanetEvent(final Planet planet) {
        List<PlanetEvent> possibleEvents = Arrays.asList(PlanetEvent.values())
                .stream()
                .filter(new Predicate<PlanetEvent>() {
                    @Override
                    public boolean test(PlanetEvent planetEvent) {
                        return !planetEvent.conflictingResources.contains(planet.getResource());
                    }
                })
                .collect(Collectors.<PlanetEvent>toList());
        return possibleEvents.get(new Random().nextInt(possibleEvents.size()));

    }
}