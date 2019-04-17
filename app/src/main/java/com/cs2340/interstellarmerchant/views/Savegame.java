package com.cs2340.interstellarmerchant.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.viewmodels.SavesViewModel;

import java.util.ArrayList;

/**
 * class that handles save screen
 */
public class Savegame extends AppCompatActivity{

    public TextInputEditText input;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private SavesViewModel svm;
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savegame);
        svm = ViewModelProviders.of(this).get(SavesViewModel.class);
        input = findViewById(R.id.save_name_input);
        save = (Button) findViewById(R.id.save_button);
        initSaveGames();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Log.d("click", input.getText().toString());
                    try {
                        svm.saveGame(input.getText().toString());
                        svm.updateSaves();
                        startActivity(new Intent(Savegame.this, OnPlanet.class));
                    } catch (Exception e) {
                        Toast.makeText(Savegame.this, "Game did not save", Toast.LENGTH_SHORT).show();

                    }
            }
        });
    }

    private void initSaveGames() {

        for (SaveOverview so :svm.getSaveList()) {
            names.add(so.getName());
            times.add(so.getLastModified().toString());
        }

        initRecyclerView();
    }
    /**
     * for the recycler view
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_load);
        SaveRecyclerViewAdapter adapter =
                new SaveRecyclerViewAdapter(names, times, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        svm.saveAdapter = adapter;
    }

    public void changeInput(String s) {
        input.setText(s);
    }

}