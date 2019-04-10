package com.cs2340.interstellarmerchant.model.universe.events.planet_events;

import junit.framework.TestCase;

import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;
import com.cs2340.interstellarmerchant.model.universe.events.Event;


import static org.junit.Assert.*;

public class TanmoyJunit extends TestCase {
    public void testDayUpdatedcomparision() {
        TimeController time = new TimeController();
        int day = time.getCurrentDay();
        int temp = day;
        PlanetEvent event = new PlanetEvent(PlanetEventType.COLD);
        int lifeRem = event.getLifeRem();
        int mrecday = event.getMostRecentDay();

        if(mrecday != -1) {
            lifeRem = lifeRem - (day - mrecday);
            assertEquals(lifeRem,event.getLifeRem());
        }

        if (lifeRem <= 0) {
            assertEquals(false, event.dayUpdated(day,time));
        } else {
            assertEquals(true, event.dayUpdated(day,time));
        }

        event = new PlanetEvent(PlanetEventType.COLD);
        lifeRem = event.getLifeRem();
        mrecday = event.getMostRecentDay();

        if(mrecday != -1) {
            lifeRem = lifeRem - (day - mrecday);
            assertEquals(lifeRem,event.getLifeRem());
        }

    }

    public  void testDayUpdatedReturns() {
        TimeController time = new TimeController();
        int day = time.getCurrentDay();
        int temp = day;
        PlanetEvent event = new PlanetEvent(PlanetEventType.COLD);
        int lifeRem = event.getLifeRem();
        int mrecday = event.getMostRecentDay();

        if (lifeRem <= 0) {
            assertEquals(false, event.dayUpdated(day,time));
        } else {
            assertEquals(true, event.dayUpdated(day,time));
        }

        lifeRem = 0;
        event.setLifeRemaining(0);
        boolean eventalive = true;
        if (lifeRem <= 0) {
            eventalive = false;
            assertEquals(eventalive, event.dayUpdated(day,time));
        } else {
            assertEquals(eventalive, event.dayUpdated(day,time));
        }
    }
}