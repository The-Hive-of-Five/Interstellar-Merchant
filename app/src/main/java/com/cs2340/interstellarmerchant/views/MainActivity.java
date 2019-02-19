package com.cs2340.interstellarmerchant.views;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.utilities.LogUtility;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createUniverse();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultySpinner = findViewById(R.id.difficulty_spinner);

        final ArrayAdapter<Difficulty> difficulties = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Difficulty.values());
        difficulties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficulties);

        Button continueBtn = (Button) findViewById(R.id.continue_btn);

        // get the edit text for the name edit
        final EditText nameEdit = ((TextInputLayout)
                findViewById(R.id.textInputLayout)).getEditText();
        // store starting text to see if changes have been made later on
        final String startingText = nameEdit.getText().toString();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // parse ints from fields
                int pilotP = parseInt(R.id.pilot_skillpts);
                int fighterP = parseInt(R.id.fighter_skillpts);
                int traderP = parseInt(R.id.trader_skillpts);
                int engineerP = parseInt(R.id.engineer_skillpts);

                // use null if the text matches the starting value
                String name = nameEdit.getText().toString().equals(startingText) ? null
                        : nameEdit.getText().toString();

                try {
                    createPlayer(name, pilotP, fighterP, traderP, engineerP,
                            (Difficulty) difficultySpinner.getSelectedItem());
                } catch (IllegalArgumentException exception) {
                    TextView result = (TextView) findViewById(R.id.char_setup_result);
                    result.setText(exception.getMessage());
                }
            }
        });
    }

    /**
     * Creates player and moves to the next screen
     * <p>
     * Throws exception if invalid arguments for creating player
     */
    private void createPlayer(String name, int pilot, int fighter, int trader, int engineer,
                              Difficulty gameDifficulty)
            throws
            IllegalArgumentException {

        Player player = new Player(pilot, fighter, trader, engineer, name,
                new GameConfig(gameDifficulty));
        Intent nextActivityIntent = new Intent(MainActivity.this,
                CharacterSummary.class);
        // send the player as a parameter
        nextActivityIntent.putExtra("player", player);
        nextActivityIntent.putExtra("universe", createUniverse());

        // start the character summary activity
        startActivity(nextActivityIntent);
    }

    /**
     * Parse int from id. Assumes is edit text field.
     *
     * @param id of the field
     * @return integer or 0 if no integer can be parsed
     */
    private int parseInt(int id) {
        int output;
        try {
            output = Integer.parseInt(((EditText) findViewById(id)).getText().toString());
        } catch (NumberFormatException ex) {
            output = 0; // default value
        }
        return output;
    }

    private Universe createUniverse() {
        Universe universe = null;
        try {
            universe = Universe.generateUniverse(getAssets()
                    .open("universe/universe.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return universe;
    }
}
