package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.repository.Database;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
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

    private Database database;
    private Universe universe;
    private String gameName;

    public GameController() {
    }

    /**
     * Inits the GameController
     * @param universe - the universe for the game
     */
    public void init (Database database, Universe universe, String gameName) {
        this.database = database;
        this.universe = universe;
        this.gameName = gameName;
    }

    /**
     * Inits the game through the game serialization in the form of a save state
     * @param state - the state of the game
     */
    public void init (Database database, SaveState state) {
        // load the details from the save state
        Player.setInstance(state.player);
        TimeController.Companion.setInstance(state.timeController);

        init(database, state.universe, state.name);
        this.universe.afterDeserialized();
    }

    /**
     * Gets the database
     * @return the database
     */
    public Database getDatabase() { return database; }

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

    /**
     * Gets the GameController as a save state
     * @return the serialization
     */
    public SaveState getSaveState() {
        return new SaveState(this.getPlayer(), this.getUniverse(),
                this.getTimeController(), gameName);
    }
}
