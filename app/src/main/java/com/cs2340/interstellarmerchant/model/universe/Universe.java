package com.cs2340.interstellarmerchant.model.universe;

import java.io.InputStream;
import java.util.List;


public class Universe {

    private List<SolarSystem> systems;

    public Universe(List<SolarSystem> systems) {
        this.systems = systems;
    }

    public static Universe generateUniverse(InputStream fileInputStream) {
        List<Planet> planets = Planet.Companion.generatePlanets(fileInputStream);
        List<SolarSystem> solarSystems = SolarSystem.Companion.generateSolarSystem(planets);
        return new Universe(solarSystems);
    }


}
