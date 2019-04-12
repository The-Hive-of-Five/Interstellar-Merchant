package com.cs2340.interstellarmerchant.model.player;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.player.ship.Ship;
import com.cs2340.interstellarmerchant.model.player.ship.ShipType;
import com.cs2340.interstellarmerchant.model.universe.market.Market;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.planet_attributes.Tech;
import com.cs2340.interstellarmerchant.model.utilities.inventory.StandardInventory;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class canBuyItemsTest {
    Player test = generatePlayer();
    Order testOrder;
    private static Market focusMarket;


    private static Player generatePlayer() {
        // instantiate the player
        return new Player(new GameConfig(Difficulty.Hard));
    }


    public static Order createMaxOrder(int max, StandardInventory market) {
        HashMap<Item, Integer> order = new HashMap<>();
        Map<Item, Integer> marketAddition = new HashMap<>();
        order.put(Item.FOOD, 20);
        Order theOrder = new Order(order);
        theOrder.setPrice(10000);
        return theOrder;
    }

    public static Order createBasicOrder(StandardInventory market) {
        HashMap<Item, Integer> order = new HashMap<>();
        Map<Item, Integer> marketAddition = new HashMap<>();
        order.put(Item.FOOD, 5);
        marketAddition.put(Item.FOOD, 5);
        Order theOrder = new Order(order);
        theOrder.setPrice(5);
        return theOrder;
    }



    @Test
    public void test() {

        //no credits branch
        test.setCredits(0); //no credits to buy
        testOrder = createBasicOrder(focusMarket);
        OrderStatus output = test.canBuyItems(testOrder);
        assertEquals(OrderStatus.NOT_ENOUGH_CREDITS, output);
        test.getShip().clearInventory();

        //all good branch
        test.setCredits(1000000); //more than enough credits
        output = test.canBuyItems(testOrder);
        assertEquals(OrderStatus.SUCCESS, output);

        //no space branch
        int avaiSpace = test.getShip().getAvailableSpace(); //avaiSpace is 15
        testOrder = createMaxOrder(avaiSpace, focusMarket); //order is size 20
        output = test.canBuyItems(testOrder);
        assertEquals(OrderStatus.NOT_ENOUGH_SPACE, output);
        test.getShip().clearInventory();
    }



}
