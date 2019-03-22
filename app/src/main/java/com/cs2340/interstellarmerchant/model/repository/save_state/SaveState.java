package com.cs2340.interstellarmerchant.model.repository.save_state;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.google.gson.Gson;

import java.util.Date;
import java.util.Random;

/**
 * Used to hold saves for the game
 */
public class SaveState {
    private static final int RANDOM_NUMBER_FOR_SAVE = 1000;
    /**
     * Auto generates a name for the save
     * @param player - the player
     * @param universe - the universe
     * @param timeController - the time controller
     */
    public static SaveState createSaveWithNoName(Player player, Universe universe,
                                                 TimeController timeController) {
       String name = "Save-" + new Random().nextInt(RANDOM_NUMBER_FOR_SAVE);
       return new SaveState(player, universe, timeController, name);
    }

    /**
     * Creates a save from a json string
     * @param json - the json string
     * @return - the save within the json string
     */
    public static SaveState saveJSONFactory(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SaveState.class);
    }

    public Date lastModified;
    public Player player;
    public String name;
    public TimeController timeController;
    public Universe universe;

    /**
     * Generates a SaveState
     * @param player - the player
     * @param universe - the universe
     * @param timeController - the time controller
     * @param name - the save's name
     */
    public SaveState(Player player, Universe universe, TimeController timeController, String name) {
        this.player = player;
        this.universe = universe;
        this.timeController = timeController;
        this.name = name;
        this.updateLastModified();
    }


    /**
     * Gets the save state as a json string
     * @return the save state as a json string
     */
    public String getSerialization() {
        Gson gson = new Gson();
        return gson.toJson(this, SaveState.class);
    }

    /**
     * Updates the last modified lastModified with the current lastModified
     */
    public void updateLastModified() {
        this.lastModified = new Date();
    }

}