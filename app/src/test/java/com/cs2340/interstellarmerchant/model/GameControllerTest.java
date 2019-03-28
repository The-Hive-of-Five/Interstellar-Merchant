package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.player.ship.ShipType;
import com.cs2340.interstellarmerchant.model.repository.MockDatabase;
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
        GameController controller = GameController.getInstance();
        controller.init(new MockDatabase(), generatePlayer(), universe,
                new TimeController(),"SAVE NAME");
    }

    @Test
    public void serializeTest() {
        String serialization = null;
        try {
            serialization = GameController.getInstance().getSaveState().getSerialization();
            System.out.println(serialization);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat("No error while serializing", serialization != null);
        assertThat("Serialization has length", !serialization.isEmpty());

    }

    @Test
    public void loadSerialization() {
        GameController controller = GameController.getInstance();
        String serialization = controller.getSaveState().getSerialization();

        GameController.clearGameController();

        controller = GameController.getInstance();
        controller.init(new MockDatabase(), SaveState.saveJSONFactory(serialization));
        assertThat("Player has name",
                controller.getPlayer().getName().length() > 0);
        assertThat(controller.getPlayer().getShipType(), is(ShipType.GNAT));
    }

    @Test
    public void loadSerializationTimeResubscribed() {
        GameController controller = GameController.getInstance();

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
