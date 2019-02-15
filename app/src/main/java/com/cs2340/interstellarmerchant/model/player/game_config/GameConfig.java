package com.cs2340.interstellarmerchant.model.player.game_config;

public class GameConfig {
    private Difficulty gameDifficulty;

    public GameConfig(Difficulty difficulty) {
        this.gameDifficulty = difficulty;
    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }
}
