package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.universe.events.planet_events.PlanetEvent;
import com.cs2340.interstellarmerchant.model.universe.planet.Planet;
import com.cs2340.interstellarmerchant.model.universe.time.TimeController;
import com.cs2340.interstellarmerchant.utilities.LogUtility;

import java.util.HashSet;

public class OnPlanet extends AppCompatActivity{

    private Button visitMarketBtn;
    private Button travelBtn;
    private Button saveButton;
    private Button loadButton;
    TextView planetText;
    TextView timeText;
    TextView eventText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onplanet);
        planetText = findViewById(R.id.planet);
        timeText = findViewById(R.id.textView2);
        eventText = findViewById(R.id.textView5);

        GameController gc = GameController.getInstance();
        Player player = gc.getPlayer();
        Planet planet  = (Planet) player.getCurrentLocation();
        planetText.setText(planet.getName());
        TimeController tc = gc.getTimeController();
        int day = tc.getCurrentDay();
        timeText.setText("Day " + day);
        HashSet<PlanetEvent> events = planet.getCurrentEvents();
        if (events.isEmpty()){
            eventText.setText("None");

        } else {
            String eventNames = "";
            for (PlanetEvent pe : events) {
                eventNames += pe.toString() + ", ";
            }
            eventText.setText(eventNames);
        }
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
