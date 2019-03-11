package com.cs2340.interstellarmerchant.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.universe.market.Market;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.model.universe.market.items.Order;
import com.cs2340.interstellarmerchant.model.universe.market.items.OrderStatus;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.views.MarketBuyRecyclerViewAdapter;
import com.cs2340.interstellarmerchant.views.MarketSellRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.util.Set;

import static android.content.ContentValues.TAG;

public class MarketViewModel extends AndroidViewModel {

    private Planet planet;
    public Market market;

    //market items for buying
    public Map marketItems;
    public ArrayList<Item> buyItemArray;


    //cargo items for selling
    public Map shipItems;
    public ArrayList<Item> sellItemArray;

    public MarketBuyRecyclerViewAdapter adapter;
    public MarketSellRecyclerViewAdapter adapter1;


    public MarketViewModel(@NonNull Application application) {
        super(application);
        planet = (Planet) Player.getInstance().getCurrentLocation();
        market = planet.getMarket();

        marketItems = market.getInventoryClone();
        Set<Item> buyKeys = marketItems.keySet();
        buyItemArray = new ArrayList<>();
        for (Item key : buyKeys) {
            buyItemArray.add(key);
        }

        shipItems = Player.getInstance().getShip().getInventoryClone();
        Set<Item> sellKeys = shipItems.keySet();
        sellItemArray = new ArrayList<>();
        for (Item key : sellKeys) {
            sellItemArray.add(key);
        }

    }

    public String getMarketItem(int i) {
        return buyItemArray.get(i).toString();
    }

    public String getMarketItemPrice(int i) {
        Item item = buyItemArray.get(i);
        return "$" + market.getItemBuyPrice(item);
    }


    //gets the item to sell at index i from market
    public String getShipItem(int i) {
        return sellItemArray.get(i).toString();
    }

    //gets the selling price from market
    public String getShipItemSellPrice(int i) {
        Item item = sellItemArray.get(i);
        return "$" + market.getItemSellPrice(item);


    }

    public void update() {
        marketItems = market.getInventoryClone();
        Set<Item> buyKeys = marketItems.keySet();
        buyItemArray = new ArrayList<>();
        for (Item key : buyKeys) {
            buyItemArray.add(key);
        }
        adapter.itemNames.clear();
        adapter.itemPrices.clear();
        for (int j = 0; j < buyItemArray.size(); j++) {
            //if (!((marketItems.get(buyItemArray.get(j))).equals(new Integer(0)))) {
                adapter.itemNames.add(getMarketItem(j));
                adapter.itemPrices.add(getMarketItemPrice(j));
            //}
        }

        shipItems = Player.getInstance().getShip().getInventoryClone();
        Set<Item> sellKeys = shipItems.keySet();
        sellItemArray = new ArrayList<>();
        for (Item key : sellKeys) {
            sellItemArray.add(key);
        }
        adapter1.itemNames.clear();
        adapter1.itemPrices.clear();
        for (int k = 0; k < sellItemArray.size(); k++) {
            Log.d(TAG, ((Integer) (shipItems.get(sellItemArray.get(k)))).toString() + " - This is the quantity of each item on the ship");
            //if (!((shipItems.get(sellItemArray.get(k))).equals(new Integer(0)))) {
                adapter1.itemNames.add(getShipItem(k));
                adapter1.itemPrices.add(getShipItemSellPrice(k));
            //}
        }


        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
        //clearForm((ViewGroup)adapter);
    }

    public OrderStatus buyItem(int amount, int i) {
        Item item = buyItemArray.get(i);
        Log.d(TAG, "the view model buyItem method amount: " + amount);
        Map<Item, Integer> map = new HashMap<>();
        map.put(item, amount);
        return market.buyItems(new Order(map), Player.getInstance());
    }

    public void sellItem(int amount, int i) {
        Item item = sellItemArray.get(i);
        Log.d(TAG, "the view model sell method amount: " + amount);
        Map<Item, Integer> map = new HashMap<>();
        map.put(item, amount);
        market.sellItems(new Order(map), Player.getInstance());
        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
    }




}
