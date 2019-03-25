package com.cs2340.interstellarmerchant.model.player;

import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("ALL")
public class PlayerTest {
    private static Player player;

    @BeforeClass
    public static void getPlanets() {
        // instatiate the player
        player = Player.getInstance();
        player.init(new GameConfig(Difficulty.Hard));
    }


    @Test
    public void serializeTest() {
        String serialization = null;
        try {
            serialization = player.serialize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat("No error while serializing", serialization != null);
        assertThat("Serialization has length", !serialization.isEmpty());

    }
}
