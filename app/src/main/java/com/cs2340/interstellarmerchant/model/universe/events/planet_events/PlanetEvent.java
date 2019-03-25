package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;
import com.cs2340.interstellarmerchant.model.universe.events.Event;

import java.io.Serializable;

public class PlanetEvent implements Event, Serializable, TimeSubscriberI {
    // maximum possible lifespan for randomly generated lifespan
    private static final int MAX_LIFE = 150;

    private boolean eventAlive;
    private int lifeSpan;
    private int mostRecentDay = -1;
    private final PlanetEventType type;


    public PlanetEvent(PlanetEventType type, int lifeSpan) {
        this.lifeSpan = lifeSpan;
        this.type = type;
        eventAlive = true;
    }

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
    public boolean dayUpdated(int day, TimeController controller) {
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
    public void onSubscribe(int day, TimeController controller) {
        this.mostRecentDay = day;
    }

    @Override
    public void onUnsubscribe(int day, TimeController controller) {
        lifeSpan = 0;
    }
}
