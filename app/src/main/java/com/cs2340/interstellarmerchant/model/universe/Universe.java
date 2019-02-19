package com.cs2340.interstellarmerchant.model.universe;

import android.util.Pair;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Universe implements Serializable {

    /**
     * Generates a universe
     * @param fileInputStream - to the planets.xml
     * @return the Universe
     */
    public static Universe generateUniverse(InputStream fileInputStream) {
        List<Planet> planets = Planet.Companion.generatePlanets(fileInputStream);
        List<SolarSystem> solarSystems = SolarSystem.Companion.generateSolarSystem(planets);
        return new Universe(solarSystems);
    }
    public static int MAX_X = 500;
    public static int MAX_Y = 500;


    private List<SolarSystem> systems;

    public Universe(List<SolarSystem> systems) {
        setLocations(systems);
        this.systems = systems;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(Locale.ENGLISH,"Universe of size %d x %d \n", Universe.MAX_X,
                Universe.MAX_Y));
        for (SolarSystem system: systems) {
            builder.append(system.toString() + "\n");
        }

        return builder.toString();
    }

    /**
     * Modifies the locations of each solar system and gives each a unique location
     * @param systems - the list of SolarSystems being modified
     */
    private void setLocations(List<SolarSystem> systems) {
        HashSet<Pair<Integer, Integer>> locations = new HashSet<>();
        Random random = new Random();
        for (SolarSystem system: systems) {
            Pair<Integer, Integer> pair;

            // ensures a unique coordinate
            do {
                int x = random.nextInt(Universe.MAX_X);
                int y = random.nextInt(Universe.MAX_Y);
                pair = new Pair<>(x, y);
            } while (locations.contains(pair));

            // modify the solar system
            system.setX(pair.first);
            system.setY(pair.second);

            // ensures the location is not reused
            locations.add(pair);
        }


    }



}
