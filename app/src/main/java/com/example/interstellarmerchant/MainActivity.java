package com.example.interstellarmerchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner difficultySpinner;

    public enum Difficulties {
        Beginner,
        Easy,
        Normal,
        Hard,
        Impossible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultySpinner = findViewById(R.id.difficulty_spinner);

        ArrayAdapter<Difficulties> difficulties = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Difficulties.values());
        difficulties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficulties);

    }
}
