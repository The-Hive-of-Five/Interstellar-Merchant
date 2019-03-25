package com.cs2340.interstellarmerchant.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cs2340.interstellarmerchant.model.GameController;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.ContentValues.TAG;

public class MarketViewModel extends AndroidViewModel {


    public AtomicBoolean retrieveEditText = new AtomicBoolean(false);

    private Planet planet;
    private Player player;

    public Market market;

    //market items for buying
    public Map marketItems;
    public ArrayList<Item> buyItemArray;


    //cargo items for selling
    public Map shipItems;
    public ArrayList<Item> sellItemArray;

    public MarketBuyRecyclerViewAdapter adapter;
    public MarketSellRecyclerViewAdapter adapter1;


    public void upudatu(){
        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
    }


    public MarketViewModel(@NonNull Application application) {
        super(application);
        GameController gameController = GameController.getInstance();
        this.player = gameController.getPlayer();

        planet = (Planet) player.getCurrentLocation();
        market = planet.getMarket();

        marketItems = market.getInventoryClone();
        Set<Item> buyKeys = marketItems.keySet();
        buyItemArray = new ArrayList<>();
        for (Item key : buyKeys) {
            buyItemArray.add(key);
        }

        shipItems = player.getShip().getInventoryClone();
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

    public String getMarketTotal(int i) {
        return (marketItems.get(buyItemArray.get(i))).toString();
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

    public String getShipTotal(int i) {
        return (shipItems.get(sellItemArray.get(i))).toString();
    }

    public void  update() {
        marketItems = market.getInventoryClone();
        Set<Item> buyKeys = marketItems.keySet();
        buyItemArray = new ArrayList<>();
        for (Item key : buyKeys) {
            buyItemArray.add(key);
        }
        adapter.itemNames.clear();
        adapter.itemPrices.clear();
        adapter.itemTotals.clear();
        for (int j = 0; j < buyItemArray.size(); j++) {
            adapter.itemNames.add(getMarketItem(j));
            adapter.itemPrices.add(getMarketItemPrice(j));
            adapter.itemTotals.add(getMarketTotal(j));
        }


        shipItems = player.getShip().getInventoryClone();
        Set<Item> sellKeys = shipItems.keySet();
        sellItemArray = new ArrayList<>();
        for (Item key : sellKeys) {
            sellItemArray.add(key);
        }
        adapter1.itemNames.clear();
        adapter1.itemPrices.clear();
        adapter1.itemTotals.clear();
        for (int k = 0; k < sellItemArray.size(); k++) {
            adapter1.itemNames.add(getShipItem(k));
            adapter1.itemPrices.add(getShipItemSellPrice(k));
            adapter1.itemTotals.add(getShipTotal(k));
        }


        adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();
    }

    public OrderStatus buyItem(int amount, int i) {
        Item item = buyItemArray.get(i);
        Map<Item, Integer> map = new HashMap<>();
        map.put(item, amount);
        return market.buyItems(new Order(map), player);
    }

    public void sellItem(int amount, int i) {
        Item item = sellItemArray.get(i);
        Map<Item, Integer> map = new HashMap<>();
        map.put(item, amount);
        market.sellItems(new Order(map), player);
    }

    public OrderStatus buyOrder() {
        Map<Item, Integer> buyOrder = new HashMap<>();
        int amount = 0;
        Item item = null;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (!(adapter.mEditTextValues.get(i).equals(""))) {
                amount = Integer.parseInt(adapter.mEditTextValues.get(i));
                item = buyItemArray.get(i);
                buyOrder.put(item, amount);
            }
        }
        return market.buyItems(new Order(buyOrder), player);
    }

    public void sellOrder() {
        Map<Item, Integer> sellOrder = new HashMap<>();
        int amount = 0;
        Item item = null;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (!(adapter1.mEditTextValues.get(i).equals(""))) {
                amount = Integer.parseInt(adapter1.mEditTextValues.get(i));
                item = sellItemArray.get(i);
                sellOrder.put(item, amount);
            }
        }
        market.sellItems(new Order(sellOrder), player);
    }




}
