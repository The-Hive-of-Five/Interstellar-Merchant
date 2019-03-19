package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;
import com.cs2340.interstellarmerchant.model.universe.events.Event;

import java.io.Serializable;

public class PlanetEvent implements Event, Serializable, TimeSubscriberI {
    // maximum possible lifespan for randomly generated lifespan
    public static final int MAX_LIFE = 150;

    private boolean eventAlive;
    private int lifeSpan;
    private int mostRecentDay = -1;
    private PlanetEventType type;


    public PlanetEvent(PlanetEventType type, int lifeSpan) {
        this.lifeSpan = lifeSpan;
        this.type = type;
        eventAlive = true;

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