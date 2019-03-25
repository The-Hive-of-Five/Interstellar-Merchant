package com.cs2340.interstellarmerchant.model.travel;


import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.repository.MockDatabase;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class TripTest {
    private static Universe universe;
    private static Player player;

    @BeforeClass
    public static void getPlanets() throws IOException {
        universe = generateUniverse();
        GameController controller = GameController.getInstance();
        controller.init(new MockDatabase(), generatePlayer(), universe,
               new TimeController(),"SAVE NAME");
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

    private static Player generatePlayer() {
        // instatiate the player
        return new Player(new GameConfig(Difficulty.Hard));
    }
}
