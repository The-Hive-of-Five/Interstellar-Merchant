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

import java.util.ArrayList;

public class Loadgame extends AppCompatActivity{

    private ArrayList<String> gameNames = new ArrayList<>();
    private ArrayList<String> gameTimes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadgame);
    }
}
