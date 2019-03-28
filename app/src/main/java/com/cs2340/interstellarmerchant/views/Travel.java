package com.cs2340.interstellarmerchant.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.travel.Location;
import com.cs2340.interstellarmerchant.model.travel.Trip;
import com.cs2340.interstellarmerchant.model.universe.SolarSystem;
import com.cs2340.interstellarmerchant.model.universe.market.items.Item;
import com.cs2340.interstellarmerchant.utilities.LogUtility;
import com.cs2340.interstellarmerchant.viewmodels.MarketViewModel;
import com.cs2340.interstellarmerchant.viewmodels.TravelViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Travel extends AppCompatActivity{

    private Button Warp;
    private TravelViewModel tvm;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    private TextView travelTime;
    private TextView travelCost;
    private TextView currentTime;
    private TextView currentCost;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel);
        tvm = ViewModelProviders.of(this).get(TravelViewModel.class);
        //......define spinner in java code...........
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        //populate an arraylist with array values
        List<String> solarSystemArray = tvm.solarSystemList();
        //Create a ArrayAdapter using arraylist and a default spinner layout
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, solarSystemArray);
        //specify the layout to appear list items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //data bind adapter with both spinners

        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tvm.planetList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

        travelTime = findViewById(R.id.travel_time);
        travelCost = findViewById(R.id.fuel_cost);
        currentTime = findViewById(R.id.currentTime);
        currentCost = findViewById(R.id.fuel);

        Trip trip = new Trip((Location) tvm.gc.getPlayer().getCurrentLocation(), (Location) tvm.gc.getUniverse().getSystems()[0].getPlanets().get(0));
        travelTime.setText(trip.getTime() + " days");
        travelCost.setText(trip.getFuelCost() + "");
        currentTime.setText("Day " + tvm.gc.getTimeController().getCurrentDay());
        currentCost.setText(tvm.gc.getPlayer().getShip().getInventoryClone().get(Item.FUEL) + "");

        Warp = (Button) findViewById(R.id.warp);
        Warp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "Traveling to Solar System " + spinner.getSelectedItem().toString());
                Log.d("click", "Traveling to planet " + spinner2.getSelectedItem().toString());
                try {
                    tvm.travel(spinner.getSelectedItem().toString(), spinner2.getSelectedItem().toString());
                    startActivity(new Intent(Travel.this, OnPlanet.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = spinner.getSelectedItem().toString();
                int index = tvm.solarSystemList().indexOf(spinnerValue);
                ArrayList<String> planets = tvm.getPlanets(index);
                ArrayAdapter<String> adap = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, planets);
                spinner2.setAdapter(adap);
                spinnerValue = spinner2.getSelectedItem().toString();
                int pindex = tvm.planetList.indexOf(spinnerValue);
                int ssindex = tvm.solarSystemList().indexOf(spinner.getSelectedItem().toString());
                Trip trip = new Trip((Location) tvm.gc.getPlayer().getCurrentLocation(), tvm.gc.getUniverse().getSystems()[ssindex].getPlanets().get(pindex));
                travelTime.setText(trip.getTime() + " days");
                travelCost.setText(trip.getFuelCost() + "");
                currentTime.setText("Day " + tvm.gc.getTimeController().getCurrentDay());
                currentCost.setText(tvm.gc.getPlayer().getShip().getInventoryClone().get(Item.FUEL) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = spinner2.getSelectedItem().toString();
                int pindex = tvm.planetList.indexOf(spinnerValue);
                int ssindex = tvm.solarSystemList().indexOf(spinner.getSelectedItem().toString());
                Trip trip = new Trip((Location) tvm.gc.getPlayer().getCurrentLocation(), tvm.gc.getUniverse().getSystems()[ssindex].getPlanets().get(pindex));
                travelTime.setText(trip.getTime() + " days");
                travelCost.setText(trip.getFuelCost() + "");
                currentTime.setText("Day " + tvm.gc.getTimeController().getCurrentDay() + "");
                currentCost.setText(tvm.gc.getPlayer().getShip().getInventoryClone().get(Item.FUEL) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }
}
