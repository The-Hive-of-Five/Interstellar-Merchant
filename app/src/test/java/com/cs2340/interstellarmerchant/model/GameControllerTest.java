package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.universe.SolarSystem;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.model.universe.time.TimeSubscriberI;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameControllerTest {
    private Player player;

    @Before
    public void instantiatePlayerUniverse() throws IOException {
        generatePlayer();
        Universe universe = generateUniverse();

        GameController controller = GameController.getInstance();
        controller.init(universe);
    }

    @Test
    public void serializeTest() {
        String serialization = null;
        try {
            serialization = GameController.getInstance().serialization();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat("No error while serializing", serialization != null);
        assertThat("Serialization has length", serialization.length() > 0);

        System.out.println(serialization);

    }

    @Test
    public void loadSerialization() {
        GameController controller = GameController.getInstance();
        String serialization = controller.serialization();
        controller.init(serialization);
    }

    @Test
    public void loadSerializationTimeResubscribed() {
        GameController controller = GameController.getInstance();
        String serialization = controller.serialization();
        controller.init(serialization);

        TimeController timeController = controller.getTimeController();
        Universe universe = controller.getUniverse();
        SolarSystem[] systems = universe.getSystems();
        for (SolarSystem system: systems) {
            for (Planet planet: system.getPlanets()) {
                assertTrue(timeController.isSubscribed(planet));
                for (TimeSubscriberI event: planet.getCurrentEvents()) {
                    assertTrue(timeController.isSubscribed(event));
                }
            }
        }
    }

    public static Universe generateUniverse() throws IOException {
        InputStream fileStream  = new FileInputStream(
                new File("src/main/assets/universe/universe.xml"));
        return Universe.generateUniverse(fileStream);
    }

    public void generatePlayer() {
        // instatiate the player
        player = Player.getInstance();
        player.init(new GameConfig(Difficulty.Hard));
    }
}
