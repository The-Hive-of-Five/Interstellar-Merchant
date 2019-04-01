package com.cs2340.interstellarmerchant.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class Savegame extends AppCompatActivity{

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savegame);

        initSaveGames();
    }

    private void initSaveGames() {
        names.add("Alice");
        times.add("15:20");

        names.add("Bob");
        times.add("14:20");

        names.add("Claire");
        times.add("9:20");

        initRecyclerView();
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_load);
        LoadRecyclerViewAdapter adapter =
                new LoadRecyclerViewAdapter(names, times, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}