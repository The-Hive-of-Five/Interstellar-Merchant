package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        Button continueBtn = (Button) findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView result = (TextView) findViewById(R.id.char_setup_result);

                int pilotP = parseInt(R.id.pilot_skillpts);
                int fighterP = parseInt(R.id.fighter_skillpts);
                int traderP = parseInt(R.id.trader_skillpts);
                int engineerP = parseInt(R.id.engineer_skillpts);
                int totalP = pilotP + fighterP + traderP + engineerP;

                if (totalP > 16) { // you don't need to allocate all your skills points
                    result.setText("Total allocated skillpoints must be 16!");
                } else {
                    startActivity(new Intent(MainActivity.this, CharacterSummary.class));
                }

            }
        });
    }

    /**
     * Parse int from id. Assumes is edit text field.
     * @param id of the field
     * @return integer or 0 if no integer can be parsed
     */
    private int parseInt(int id) {
        int output;
        try {
            output = Integer.parseInt(((EditText) findViewById(id)).toString());
        } catch (NumberFormatException ex) {
            output = 0; // default value
        }
        return output;
    }
}
