package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.travel.TravelController;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.google.gson.Gson;

import javax.inject.Singleton;

@Singleton
public class GameController {
    private static GameController controller;

    /**
     * Gets the instance of travel controller
     * @return the travel controller
     */
    public static GameController getInstance() {
        if (controller == null) {
            controller = new GameController();
        }
        return controller;
    }

    private Universe universe;

    /**
     * Inits the GameController
     * @param universe - the universe for the game
     */
    public void init (Universe universe) {
        this.universe = universe;
    }

    /**
     * Inits the game through the game state string
     * @param gameState - the state of the game
     */
    public void init (String gameState) {
        SaveState state = getSaveStateFromSerialization(gameState);
        Player.setInstance(state.player);
        TimeController.Companion.setInstance(state.timeController);
        this.universe = state.universe;
        this.universe.afterDeserialized();
    }

    /**
     * Gets the player
     * @return the player
     */
    public Player getPlayer() {
        return Player.getInstance();
    }

    /**
     * Gets the time controller
     * @return the time controller
     */
    public TimeController getTimeController() {
        return TimeController.Companion.getTimeController();
    }

    /**
     * Gets the TravelController
     * @return the TravelController
     */
    public TravelController getTravelController() {
        return TravelController.getInstance();
    }

    /**
     * Gets the universe
     * @return the universe
     */
    public Universe getUniverse() {
        return universe;
    }

    private class SaveState {
        public Player player;
        public TimeController timeController;
        public Universe universe;

        private SaveState(Player player, Universe universe, TimeController timeController) {
            this.player = player;
            this.universe = universe;
            this.timeController = timeController;
        }
    }

    private SaveState getSaveStateFromSerialization(String serialization) {
        return new Gson().fromJson(serialization, SaveState.class);
    }

    /**
     * Serializes the object
     * @return the serialization
     */
    public String serialization() {
        Gson gson = new Gson();
        return gson.toJson(new SaveState(this.getPlayer(), this.getUniverse(),
                this.getTimeController()));
    }
}
