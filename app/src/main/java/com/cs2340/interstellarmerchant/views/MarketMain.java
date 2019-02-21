package com.cs2340.interstellarmerchant.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class MarketMain extends AppCompatActivity{

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);

        initShopItems();
    }

    private void initShopItems() {
        itemNames.add("Wood");
        itemPrices.add("$100000000");

        itemNames.add("More wood");
        itemPrices.add("$1");

        itemNames.add("time machine");
        itemPrices.add("$3");
    }
}
