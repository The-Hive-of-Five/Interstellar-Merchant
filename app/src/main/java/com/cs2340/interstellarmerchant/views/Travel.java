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

public class Travel extends AppCompatActivity{

    private Button Warp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel);


        Warp = (Button) findViewById(R.id.warp);
        Warp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Travel.this, OnPlanet.class));
            }
        });

    }
}
