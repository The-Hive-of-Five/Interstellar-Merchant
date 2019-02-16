package com.cs2340.interstellarmerchant.model.player.game_config;

import java.io.Serializable;

public class GameConfig implements Serializable {
    private Difficulty gameDifficulty;

    public GameConfig(Difficulty difficulty) {
        this.gameDifficulty = difficulty;
    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(Difficulty gameDifficulty) {
        if (gameDifficulty == null) {
            throw new IllegalArgumentException("Game difficulty can't be null");
        }
        this.gameDifficulty = gameDifficulty;
    }
}
