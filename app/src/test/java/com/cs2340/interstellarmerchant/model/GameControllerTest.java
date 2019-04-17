package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.player.ship.ShipType;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("ALL")
public class GameControllerTest {
    private Player player;

    @Before
    public void instantiatePlayerUniverse() throws IOException {
        Universe universe = generateUniverse();
        GameController.clearGameController();
        GameController controller = GameController.getTestInstance();
        controller.init(generatePlayer(), universe,
                new TimeController(),"SAVE NAME");
        Player player = controller.getPlayer();
        player.setLocation(universe.getSystems()[0].getPlanets().get(0));
    }

    @Test
    public void serializeTest() {
        String serialization = null;
        try {
            serialization = GameController.getTestInstance().getSaveState().getSerialization();
            System.out.println(serialization);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat("No error while serializing", serialization != null);
        assertThat("Serialization has length", !serialization.isEmpty());

    }

    @Test
    public void loadSerialization() {
        GameController controller = GameController.getTestInstance();

        String serialization = controller.getSaveState().getSerialization();
        System.out.println(serialization);
        GameController.clearGameController();

        controller = GameController.getTestInstance();
        controller.init(SaveState.saveJSONFactory(serialization));
        assertThat("Player has name",
                controller.getPlayer().getName().length() > 0);
        assertThat(controller.getPlayer().getShipType(), is(ShipType.GNAT));
    }

    @Test
    public void loadSerializationTimeResubscribed() {
        GameController controller = GameController.getTestInstance();

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

    public Player generatePlayer() {
        // instatiate the player
        return new Player(new GameConfig(Difficulty.Hard));
    }
}
