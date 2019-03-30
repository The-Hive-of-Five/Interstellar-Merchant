package com.cs2340.interstellarmerchant.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs2340.interstellarmerchant.R;

import java.util.ArrayList;

public class Loadgame extends AppCompatActivity{

    private ArrayList<String> gameNames = new ArrayList<>();
    private ArrayList<String> gameTimes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadgame);
    }
}
