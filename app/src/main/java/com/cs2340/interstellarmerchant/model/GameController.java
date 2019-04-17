package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.repository.Database;
import com.cs2340.interstellarmerchant.model.repository.MockDatabaseProvider;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
import com.cs2340.interstellarmerchant.model.travel.TravelController;
import com.cs2340.interstellarmerchant.model.universe.SolarSystem;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

/**
 * The GameController for the entire game. Keeps track of player, universe, time controller,
 * database...
 *
 * A Singleton
 */
public class GameController {


    @Nullable
    private static GameController controller;

    /**
     * Has the GameController already been initialized
     * @return if the game controller has already been initialized
     */
    public static boolean gameControllerAlreadyInitialized() {
        return controller != null && GameController.getInstance().initialized;
    }

    /**
     * Gets the instance of travel controller
     * @return the travel controller
     */
    @SuppressWarnings("NonThreadSafeLazyInitialization")
    public static GameController getInstance() {
        if (controller == null) {
            controller = DaggerGameControllerMaker.builder().build().get();
        }
        return controller;
    }
    public static GameController getTestInstance() {
        if (controller == null) {
            controller = DaggerGameControllerMaker.builder()
                    .databaseProvider(new MockDatabaseProvider()).build().get();
        }
        return controller;
    }


    /**
     * Uninitializes the existing game controller
     */
    public static void clearGameController() {
        controller = null;
    }

    @SuppressWarnings("RedundantFieldInitialization")
    private boolean initialized = false;

    private Database database;

    private Player player;
    private TimeController timeController;
    private TravelController travelController;
    private Universe universe;
    private String gameName;

    /**
     * Inits the GameController
     * @param player - the player for the game
     * @param universe - the universe for the game
     * @param timeController - the time controller for the game
     * @param gameName - the name of the game
     */
    public void init (Player player, Universe universe,
                      TimeController timeController, String gameName) {
        if (initialized) {
            throw new IllegalStateException("Trying to reininitialize the game controller" +
                    " after it has been initiailized");
        }

        this.player = player;
        this.universe = universe;
        this.timeController = timeController;
        this.travelController = player;
        this.gameName = gameName;
        this.initialized = true;

        subscribePlanetsToTime();
    }

    /**
     * Inits the game through the game serialization in the form of a save state
     * @param state - the state of the game
     */
    @SuppressWarnings("FeatureEnvy")
    public void init (SaveState state) {
        if (initialized) {
            throw new IllegalStateException("Trying to reininitialize the game controller" +
                    "after it has been initiailized. Call clearGameController");
        }
        // load the details from the save state
        state.callAfterDeserialized();
        Universe universe = state.getUniverse();

        init(state.getPlayer(), state.getUniverse(), state.getTimeController(),
                state.getName());

    }

    @Inject
    public GameController(Database database) {
        this.database = database;
    }

    /**
     * Gets the database
     * @return the database
     */
    public Database getDatabase() {
        checkInitialized();
        if (database == null) {
            throw new IllegalStateException("Database can't be null");
        }
        return database; }

    /**
     * Gets the player
     * @return the player
     */
    public Player getPlayer() {
        checkInitialized();
        return player;
    }

    /**
     * Gets the time controller
     * @return the time controller
     */
    public TimeController getTimeController() {
        checkInitialized();
        return timeController;
    }

    /**
     * Gets the TravelController
     * @return the TravelController
     */
    public TravelController getTravelController() {
        checkInitialized();
        return travelController;
    }

    /**
     * Gets the universe
     * @return the universe
     */
    public Universe getUniverse() {
        checkInitialized();
        return universe;
    }

    /**
     * Gets the GameController as a save state
     * @return the serialization
     */
    public SaveState getSaveState() {
        checkInitialized();
        return new SaveState(this.getPlayer(), this.getUniverse(),
                this.getTimeController(), gameName);
    }

    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Trying to call public method before" +
                    "GameController's init method has been called");
        }
    }

    private void subscribePlanetsToTime() {
        for (SolarSystem system: universe.getSystems()) {
            for (Planet planet: system.getPlanets()) {
                this.timeController.subscribeToTime(planet);
            }
        }
    }
}
