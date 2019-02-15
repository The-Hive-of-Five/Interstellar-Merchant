package com.cs2340.interstellarmerchant.model.player;

import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;

public class Player {
    public static final int MAXIMUM_POINTS = 16;

    // skill points mapping for the skill area
    public static final int PILOT = 0;
    public static final int FIGHTER = 1;
    public static final int TRADER = 2;
    public static final int ENGINEER = 3;

    private GameConfig config;
    private int credits;
    private String name;
    private int[] skillPoints; // each index represents a skill field

    public Player() {
        skillPoints = new int[4]; // their are 4 skill fields; see skill point mapping values above
    }

    /**
     * Finds all the points not invested by the playter
     * @return the skills points that have not ben invested by the player
     */
    public int getAvailableSkillPoints() {
        return Player.MAXIMUM_POINTS - getTotalSkillPoints();
    }

    /**
     * Sums all of the skill points invested by a player
     * @return all the skill points invested by a player
     */
    public int getTotalSkillPoints() {
        int sum = 0;
        for (int skill: skillPoints) {
            sum += skill;
        }
        return sum;
    }

}
