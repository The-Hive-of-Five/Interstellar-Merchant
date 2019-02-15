package com.cs2340.interstellarmerchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.example.interstellarmerchant.R;

public class MainActivity extends AppCompatActivity {

    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultySpinner = findViewById(R.id.difficulty_spinner);

        ArrayAdapter<Difficulty> difficulties = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Difficulty.values());
        difficulties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficulties);

    }
}
