package com.cs2340.interstellarmerchant.model.travel;


import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class TripTest {
    private static Universe universe;

    @BeforeClass
    public static void getPlanets() throws IOException {
        universe = generateUniverse();
    }

    @Test
    public void solarSystemTravelTest() {
        Planet one = universe.getSystems()[0].getPlanets().get(0);
        Planet two = universe.getSystems()[1].getPlanets().get(0);
        Trip trip = new Trip(one, two);
        assertThat("Fuel is greater than 0", trip.getFuelCost() > 0);

    }

    public static Universe generateUniverse() throws IOException {
        InputStream fileStream  = new FileInputStream(
                new File("src/main/assets/universe/universe.xml"));
        return Universe.generateUniverse(fileStream);
    }
}
