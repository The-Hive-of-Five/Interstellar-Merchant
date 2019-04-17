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
 * handles load view
 */
public class Loadgame extends AppCompatActivity{

    public TextInputEditText input;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private SavesViewModel svm;
    private Button load;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadgame);
        svm = ViewModelProviders.of(this).get(SavesViewModel.class);
        input = findViewById(R.id.names);
        load = findViewById(R.id.load_button);
        initLoadGames();
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("click", input.getText().toString());
                    svm.loadGame(input.getText().toString());
                    startActivity(new Intent(Loadgame.this, OnPlanet.class));

                } catch (Exception e) {
                    Toast.makeText(Loadgame.this, "Game did not load", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void initLoadGames() {
        for (SaveOverview so :svm.getSaveList()) {
            names.add(so.getName());
            times.add(so.getLastModified().toString());
        }

        initRecyclerView();
    }

    /**
     * recycler view with games
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_load);
        LoadRecyclerViewAdapter adapter =
                new LoadRecyclerViewAdapter(names, times, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        svm.loadAdapter = adapter;

    }

    public void changeInput(String s) {
        input.setText(s);
    }


}