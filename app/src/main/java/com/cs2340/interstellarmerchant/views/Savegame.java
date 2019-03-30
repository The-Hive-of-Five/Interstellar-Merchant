package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class Savegame extends AppCompatActivity{

    private ArrayList<String> gameNames = new ArrayList<>();
    private ArrayList<String> gameTimes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savegame);

        Button save = (Button) findViewById(R.id.save_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Savegame.this, SavePop.class));
            }
        });
    }
}
