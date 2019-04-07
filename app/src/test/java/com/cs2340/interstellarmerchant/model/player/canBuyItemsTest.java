package com.cs2340.interstellarmerchant.model.player;

import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class canBuyItemsTest {
    Order testOrder;

    @Test
    public void test() {
        //initialize
        Player test = new Player();


        //no space branch
        OrderStatus output = test.canBuyItems(testOrder);
        assertEquals(OrderStatus.NOT_ENOUGH_SPACE, output);

        //no credits branch
        assertEquals(OrderStatus.NOT_ENOUGH_CREDITS, output);

        //all good branch
        assertEquals(OrderStatus.SUCCESS, output);
    }

    //set inventory to max
    //set credits to non
    //set inventory to empty and credits to enough

}
