package com.cs2340.interstellarmerchant.model.universe.market;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.universe.Planet;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.market.items.MarketItem;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech;
import com.cs2340.interstellarmerchant.utilities.Inventory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MarketTest {
    private static Planet focusPlanet;
    private static Player player;
    private static Market focusMarket;

    @BeforeClass
    public static void getPlanets() throws IOException {
        focusPlanet = generatePlanets().get(0);
        focusPlanet.setTech(Tech.HI_TECH); // set to hi tech so tech does not limit market
        focusMarket = focusPlanet.getMarket();

        // create the player
        player = new Player();
        player.init(new GameConfig(Difficulty.Hard));
    }

    @Before
    public void resetMarket() {
        // clear the market
        focusMarket.clearInventory();
    }

    @Test
    public void assertPlanetValidityTest() {
        assert(focusPlanet.getName().equals("Tatooine"));
        // make sure the market has items
        assert(focusMarket.getUsedSpace() == 0);
    }

    @Test
    public void addItemTest() {
        marketIsEmpty();

        // add food
        addItemsToMarket(focusMarket);

        assertThat(focusMarket.getUsedSpace(), is(5));
        assertThat(focusMarket.getAvailableSpace(), is(Inventory.DEFAULT_MAX - 5));
    }

    @Test
    public void buyFromMarketTest() {
        marketIsEmpty();

        // add five food to market
        addItemsToMarket(focusMarket);

        focusMarket.buyItems(generateOrder(), player);

    }


    /**
     * Ensures the market is empty
     */
    public void marketIsEmpty() {
        assertThat(focusMarket.getUsedSpace(), is(0));
        assertThat(focusMarket.getAvailableSpace(), is(Inventory.DEFAULT_MAX));
    }

    /**
     * Adds five food to the market
     * @param market - the market to add food to
     */
    public static void addItemsToMarket(Market market) {
        // add 5 food to the market
        HashMap<MarketItem, Integer> marketAddition = new HashMap<>();
        marketAddition.put(new MarketItem(Item.FOOD, focusPlanet), 5);

        market.plusAssign(marketAddition);
    }

    /**
     *
     * @return
     */
    public static Order generateOrder() {
        HashMap<MarketItem, Integer> marketAddition = new HashMap<>();
        marketAddition.put(new MarketItem(Item.FOOD, focusPlanet), 5);
        return new Order(marketAddition);
    }


    public static List<Planet> generatePlanets() throws IOException {
        InputStream fileStream  = new FileInputStream(
                new File("src/main/assets/universe/universe.xml"));
        return Planet.Companion.generatePlanets(fileStream);
    }

}