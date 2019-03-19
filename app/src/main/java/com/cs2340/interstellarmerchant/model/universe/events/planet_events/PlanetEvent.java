package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;
import com.cs2340.interstellarmerchant.model.universe.events.Event;

public class PlanetEvent implements Event, TimeSubscriberI {
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
    public boolean dayUpdated(int day) {
        if (mostRecentDay != -1) {
            // get time jump since last updated
            lifeSpan -= day - mostRecentDay;
        }
        mostRecentDay = day;

        return lifeSpan != 0;
    }

    @Override
    public void onSubscribe(int day) {
        this.mostRecentDay = day;
    }

    @Override
    public void unsubscribe(int day) {
        eventAlive = false;
    }
}
