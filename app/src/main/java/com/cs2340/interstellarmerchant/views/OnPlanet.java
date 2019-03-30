package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;

import java.util.HashSet;

public class OnPlanet extends AppCompatActivity{

    private Button visitMarketBtn;
    private Button travelBtn;
    private Button saveButton;
    private Button loadButton;
    private TextView planetText;
    private TextView timeText;
    private TextView eventText;

    private GameController gc;
    private Player player;
    private TimeController tc;
    private HashSet<PlanetEvent> events;
    private int day;
    private Planet planet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onplanet);
        planetText = findViewById(R.id.planet);
        timeText = findViewById(R.id.textView2);
        eventText = findViewById(R.id.textView5);

        gc = GameController.getInstance();
        player = gc.getPlayer();
        planet  = (Planet) player.getCurrentLocation();
        planetText.setText(planet.getName());
        tc = gc.getTimeController();
        day = tc.getCurrentDay();
        timeText.setText("Day " + day);
        events = planet.getCurrentEvents();
        if (events.isEmpty()){
            eventText.setText("None");

        } else {
            String eventNames = "";
            for (PlanetEvent pe : events) {
                eventNames += pe.toString() + "\n";
            }
            Log.d("events", eventNames);

            eventText.setText(eventNames);
        }
        visitMarketBtn = (Button) findViewById(R.id.to_market);
        travelBtn = (Button) findViewById(R.id.travel);
        loadButton = (Button) findViewById(R.id.to_load);

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

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnPlanet.this, Loadgame.class));
            }
        });

    }
}
