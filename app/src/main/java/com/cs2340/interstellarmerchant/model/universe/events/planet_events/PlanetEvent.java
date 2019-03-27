package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;
import com.cs2340.interstellarmerchant.model.universe.events.Event;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represents events on planets
 */
public class PlanetEvent implements Event, Serializable, TimeSubscriberI {
    // maximum possible lifespan for randomly generated lifespan
    private static final int MAX_LIFE = 150;

    private boolean eventAlive;
    private int lifeSpan;
    private int mostRecentDay = -1;
    private final PlanetEventType type;

    /**
     * Constructor for planet event
     * @param type - the type of event
     * @param lifeSpan - the life span in universe days for the event
     */
    public PlanetEvent(PlanetEventType type, int lifeSpan) {
        this.lifeSpan = lifeSpan;
        this.type = type;
        eventAlive = true;
    }

    /**
     * Constructor for planet event. Life span is randomly determined
     * @param type - the type of planet event.
     */
    public PlanetEvent(PlanetEventType type) {
        this(type, (int) (Math.random() * PlanetEvent.MAX_LIFE));
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof PlanetEvent)) {
            return false;
        }
        PlanetEvent otherEvent = (PlanetEvent) other;
        return this.type == otherEvent.type;

    }

    @Override
    public boolean eventExpired() {
        return !eventAlive;
    }

    @Override
    public boolean dayUpdated(int day, @NotNull TimeController controller) {
        if (mostRecentDay != -1) {
            // get time jump since last updated
            lifeSpan -= day - mostRecentDay;
        }
        mostRecentDay = day;

        if (lifeSpan <= 0) {
            eventAlive = false;
        }

        return eventAlive;
    }

    @Override
    public void onSubscribe(int day, @NotNull TimeController controller) {
        this.mostRecentDay = day;
    }

    @Override
    public void onUnsubscribe(int day, @NotNull TimeController controller) {
        lifeSpan = 0;
    }

    @NotNull
    @Override
    public String toString() {
        return type.name();
    }
}
