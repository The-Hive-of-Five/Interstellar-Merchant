package com.cs2340.interstellarmerchant.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.universe.market.Market;
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;

import java.util.ArrayList;

public class MarketMain extends AppCompatActivity{

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private ArrayList<String> itemTotals = new ArrayList<>();

    private ArrayList<String> cargoNames = new ArrayList<>();
    private ArrayList<String> cargoPrices = new ArrayList<>();
    private ArrayList<String> cargoTotals = new ArrayList<>();

    private MarketViewModel marketViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        marketViewModel = ViewModelProviders.of(this).get(MarketViewModel.class);

        initShopItems();
        initCargoItems();
    }

    private void initShopItems() {
        for (int i = 0; i < marketViewModel.buyItemArray.size(); i++) {
            itemNames.add(marketViewModel.getMarketItem(i));
            itemPrices.add(marketViewModel.getMarketItemPrice(i));
            itemTotals.add("1");
        }

        initRecyclerView();
    }

    private void initCargoItems() {
        for (int i = 0; i < marketViewModel.sellItemArray.size(); i++) {
            cargoNames.add(marketViewModel.getShipItem(i));
            cargoPrices.add(marketViewModel.getShipItemSellPrice(i));
            cargoTotals.add("5");
        }

        initRecyclerView1();
    }






    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_market);
        MarketBuyRecyclerViewAdapter adapter =
                new MarketBuyRecyclerViewAdapter(itemNames, itemPrices, this, itemTotals, marketViewModel);
      
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marketViewModel.adapter = adapter;
    }

    private void initRecyclerView1() {
        RecyclerView recyclerView1 = findViewById(R.id.recycler_market_cargo);
        MarketSellRecyclerViewAdapter adapter1 =
                new MarketSellRecyclerViewAdapter(cargoNames, cargoPrices, this, itemTotals, marketViewModel);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        marketViewModel.adapter1 = adapter1;
    }

    private int parseInt(int id) {
        int output;
        try {
            output = Integer.parseInt(((EditText) findViewById(id)).getText().toString());
        } catch (NumberFormatException ex) {
            output = 0; // default value
        }
        return output;
    }

}
