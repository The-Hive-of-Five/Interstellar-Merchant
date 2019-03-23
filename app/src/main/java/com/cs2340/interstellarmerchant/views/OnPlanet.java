package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.utilities.LogUtility;

public class OnPlanet extends AppCompatActivity{

    private Button visitMarketBtn;
    private Button travelBtn;
    private Button saveButton;
    private Button loadButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onplanet);


        visitMarketBtn = (Button) findViewById(R.id.to_market);
        travelBtn = (Button) findViewById(R.id.travel);
        visitMarketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnPlanet.this, MarketMain.class));
            }
        });

        travelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnPlanet.this, Travel.class));
            }
        });

    }
}
