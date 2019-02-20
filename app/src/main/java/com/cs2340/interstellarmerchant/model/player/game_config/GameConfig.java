package com.cs2340.interstellarmerchant.model.player.game_config;

import java.io.Serializable;

/**
 * Represents the configuration of the game
 */
public class GameConfig implements Serializable {
    private Difficulty gameDifficulty;

    /**
     * constructor for the game config
     * @param difficulty - the difficulty of the game
     */
    public GameConfig(Difficulty difficulty) {
        this.gameDifficulty = difficulty;
    }

    /**
     * get the game difficulty
     * @return the game difficulty
     */
    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    /**
     * sets the game difficulty
     * @param gameDifficulty - the game difficulty
     */
    public void setGameDifficulty(Difficulty gameDifficulty) {
        if (gameDifficulty == null) {
            throw new IllegalArgumentException("Game difficulty can't be null");
        }
        this.gameDifficulty = gameDifficulty;
    }
}
