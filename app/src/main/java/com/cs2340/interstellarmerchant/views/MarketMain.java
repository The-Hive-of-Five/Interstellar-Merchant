package com.cs2340.interstellarmerchant.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;

import java.util.ArrayList;

/**
 * handles the main market view
 */
public class MarketMain extends AppCompatActivity{

    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private ArrayList<String> itemTotals = new ArrayList<>();

    private ArrayList<String> cargoNames = new ArrayList<>();
    private ArrayList<String> cargoPrices = new ArrayList<>();
    private ArrayList<String> cargoTotals = new ArrayList<>();

    private MarketViewModel marketViewModel;

    private Player player;

    TextView credits;
    TextView cargo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        marketViewModel = ViewModelProviders.of(this).get(MarketViewModel.class);

        initShopItems();
        initCargoItems();

        Button buyButton = findViewById(R.id.load_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buyListener(v);
            }
        });
        Button sellButton = findViewById(R.id.sell_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sellListener(v);
            }
        });

        GameController gameController = GameController.getInstance();
        player = gameController.getPlayer();

        credits = findViewById(R.id.textView9);
        credits.setText(player.getCredits() + "");
        cargo = findViewById(R.id.textView14);
        cargo.setText(player.getShip().getAvailableSpace() + "");

    }

    /**
     * initialize shop items
     */
    private void initShopItems() {
        for (int i = 0; i < marketViewModel.buyItemArray.size(); i++) {
            itemNames.add(marketViewModel.getMarketItem(i));
            itemPrices.add(marketViewModel.getMarketItemPrice(i));
            itemTotals.add(marketViewModel.getMarketTotal(i));
        }
        initRecyclerView();
    }
    /**
     * initialize cargo items
     */
    private void initCargoItems() {
        for (int i = 0; i < marketViewModel.sellItemArray.size(); i++) {
            cargoNames.add(marketViewModel.getShipItem(i));
            cargoPrices.add(marketViewModel.getShipItemSellPrice(i));
            cargoTotals.add(marketViewModel.getShipTotal(i));
        }
        initRecyclerView1();
    }

    /**
     * listener for buy
     * @param view passed view
     */
    private void buyListener(View view) {
        try {
            marketViewModel.buyOrder();
        } catch (Exception e){
            Log.d("BUY", e.getMessage());
        }
        marketViewModel.update();
        credits.setText(player.getCredits() + "");
        cargo.setText(player.getShip().getAvailableSpace() + "");
    }

    /**
     * listener for sell
     * @param view passed view
     */
    private void sellListener(View view) {
        try {
            marketViewModel.sellOrder();
        } catch (Exception e) {
            Log.d("BUY", "sell did not go through");
        }
        marketViewModel.update();
        credits.setText(player.getCredits() + "");
        cargo.setText(player.getShip().getAvailableSpace() + "");
    }

    /**
     * initialize recycler view for buy
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_market);
        MarketBuyRecyclerViewAdapter adapter =
                new MarketBuyRecyclerViewAdapter(itemNames, itemPrices, this, itemTotals, marketViewModel);
      
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marketViewModel.adapter = adapter;
    }
    /**
     * initialize recycler view for sell
     */
    private void initRecyclerView1() {
        RecyclerView recyclerView1 = findViewById(R.id.recycler_market_cargo);
        MarketSellRecyclerViewAdapter adapter1 =
                new MarketSellRecyclerViewAdapter(cargoNames, cargoPrices, this, cargoTotals, marketViewModel);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        marketViewModel.adapter1 = adapter1;
    }


}
