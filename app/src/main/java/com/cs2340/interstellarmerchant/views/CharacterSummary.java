package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.utilities.LogUtility;

/**
 * controls the character summary
 */
public class CharacterSummary extends AppCompatActivity {
    private Player player;
    private TextView nameText;
    private TextView pilot;
    private TextView fighter;
    private TextView trader;
    private TextView engineer;
    private TextView difficulty;
    private TextView credits;
    private TextView spaceShip;
    private TextView remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the intent used to launch the activity
        Intent intent = getIntent();

        // get the player
        this.player = (Player) intent.getSerializableExtra("player");
        LogUtility.log("UNIVERSE", intent.getSerializableExtra("universe").toString());

        // set layout
        setContentView(R.layout.character_summary);


        // get the text fields
        nameText = findViewById(R.id.textView16);
        pilot = findViewById(R.id.pilotpts);
        fighter = findViewById(R.id.fighterpts);
        trader = findViewById(R.id.traderpts);
        engineer = findViewById(R.id.engineerpts);
        difficulty = findViewById(R.id.difficulty);
        credits = findViewById(R.id.credits);
        spaceShip = findViewById(R.id.spaceship);
        remaining = findViewById(R.id.remskillpts);


        // set the values
        nameText.setText(player.getName());
        difficulty.setText(player.getDifficulty() + "");
        credits.setText(String.valueOf(player.getCredits()));
        spaceShip.setText(player.getShipType() + "");
        remaining.setText(String.valueOf(player.getAvailableSkillPoints()));
        int[] skills = player.getSkillPoints();
        pilot.setText(String.valueOf(skills[Player.PILOT]));
        fighter.setText(String.valueOf(skills[Player.FIGHTER]));
        trader.setText(String.valueOf(skills[Player.TRADER]));
        engineer.setText(String.valueOf(skills[Player.ENGINEER]));
    }
}