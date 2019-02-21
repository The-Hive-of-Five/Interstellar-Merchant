package com.cs2340.interstellarmerchant.model.player;

import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.player.ship.Ship;
import com.cs2340.interstellarmerchant.model.player.ship.ShipType;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;

import java.io.Serializable;

/**
 * Represents a Player in the game. A singleton
 */
public class Player implements Serializable  {
    private static final int MAXIMUM_POINTS = 16;
    private static final int STARTING_CREDITS = 1000;

    // skill points mapping for the skill area
    public static final int PILOT = 0;
    public static final int FIGHTER = 1;
    public static final int TRADER = 2;
    public static final int ENGINEER = 3;

    // player singleton instance
    private static Player player;

    private GameConfig config;
    private int credits;
    private Ship ship;
    private String name;
    private int[] skillPoints; // each index represents a skill

    /**
     * gets the singleton instance. call init after to send parameters
     * @return the singleton instance
     */
    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    /**
     * the constructor for the Player
     *
     * @param skills - the skills of the player (max size of 4)
     * @param ship - the player's ship
     * @param name - the name of the player
     * @param config - the game config
     */
    public void init(int[] skills, Ship ship, String name, GameConfig config) {
        // handle skill points
        if (skills.length != 4) {
            throw new IllegalArgumentException("The skills array is invalid");
        }
        this.skillPoints = skills.clone();
        if (!appropriateNumberOfSkillPoints()) {
            throw new IllegalArgumentException("You have more points than the max of " +
            Player.MAXIMUM_POINTS);
        }

        // handle other parameters
        this.ship = ship;
        this.credits = Player.STARTING_CREDITS;
        if ((name == null) || name.isEmpty()) {
            throw new IllegalArgumentException("You must give the player a name");
        } else {
            this.name = name;
        }

        if (config == null) {
            throw new IllegalArgumentException("Game config can't be null");
        } else {
            this.config = config;
        }

    }

    /**
     * the constructor for the player. gives the player a default ship
     * @param pilot - the pilot skill
     * @param fighter - the fighter skill
     * @param trader - the trader skill
     * @param engineer - the engineer skill
     * @param name - the name skill
     * @param config - the game config
     */
    public void init(int pilot, int fighter, int trader, int engineer, String name,
                  GameConfig config) {
        init(new int[] {pilot, fighter, trader, engineer}, new Ship(ShipType.GNAT), name, config);
    }

    /**
     * the constructor for the player. gives players defaults for all values
     * @param config - the game config
     */
    public void init(GameConfig config) {
        init(0, 0, 0, 0,
                "Default name", config);
    }

    /**
     * Whether the player can buy the items in the order
     * @param order - the player's order
     * @return whether the player can buy the items
     */
    public OrderStatus canBuyItems(Order order) {
        OrderStatus output;
        if (order.getQuantity() > ship.getAvailableSpace()) {
            output = OrderStatus.NOT_ENOUGH_SPACE;
        } else if (order.getTotalCost() > credits) {
            output = OrderStatus.NOT_ENOUGH_CREDITS;
        } else {
            output = OrderStatus.SUCCESS;
        }
        return output;
    }

    /**
     * Returns true if the player does not have more than the max number of skill points
     *
     * @return if the player does not have more than the max skill points
     */
    public final boolean appropriateNumberOfSkillPoints() {
        return getTotalSkillPoints() <= Player.MAXIMUM_POINTS;
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

    /**
     * gets the game config
     * @return gets the game config
     */
    public GameConfig getConfig() {
        return config;
    }

    /**
     * gets the game difficulty
     * @return the game difficulty
     */
    public Difficulty getDifficulty() {
        return config.getGameDifficulty();
    }

    /**
     * Sets the credits
     * @param newValue - the new value of the credits
     */
    public void setCredits(int newValue) {
        this.credits = newValue;
    }

    /**
     * gets the player's credits
     * @return the player's credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * gets the player's name
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the player's ship
     * @return the player's ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * get's the player's ship type
     * @return the player's ship type
     */
    public ShipType getShipType() {
        return ship.getType();
    }

    /**
     * gets the player's skill points
     * @return the player's skill points
     */
    public int[] getSkillPoints() {
        return skillPoints.clone();
    }
}
