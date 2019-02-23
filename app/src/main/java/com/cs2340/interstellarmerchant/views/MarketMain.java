package com.cs2340.interstellarmerchant.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class MarketMain extends AppCompatActivity{

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private ArrayList<String> itemTotals = new ArrayList<>();

    private ArrayList<String> cargoNames = new ArrayList<>();
    private ArrayList<String> cargoPrices = new ArrayList<>();
    private ArrayList<String> cargoTotals = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);

        initShopItems();
        initCargoItems();
    }

    private void initShopItems() {
        itemNames.add("Wood");
        itemPrices.add("$2");
        itemTotals.add("200");

        itemNames.add("More wood");
        itemPrices.add("$1");
        itemTotals.add("400");

        itemNames.add("time machine");
        itemPrices.add("$3");
        itemTotals.add("1");

        initRecyclerView();
    }

    private void initCargoItems() {
        cargoNames.add("Metal");
        cargoPrices.add("$2");
        cargoTotals.add("30");

        cargoNames.add("More metal");
        cargoPrices.add("$1");
        cargoTotals.add("25");

        cargoNames.add("not machine");
        cargoPrices.add("$3");
        cargoTotals.add("5");

        initRecyclerView1();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_market);
        MarketBuyRecyclerViewAdapter adapter =
                new MarketBuyRecyclerViewAdapter(itemNames, itemPrices, itemTotals, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initRecyclerView1() {
        RecyclerView recyclerView1 = findViewById(R.id.recycler_market_cargo);
        MarketSellRecyclerViewAdapter adapter1 =
                new MarketSellRecyclerViewAdapter(cargoNames, cargoPrices, itemTotals, this);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
    }


}
