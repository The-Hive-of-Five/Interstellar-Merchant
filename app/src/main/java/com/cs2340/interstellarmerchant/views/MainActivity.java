package com.cs2340.interstellarmerchant.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs2340.interstellarmerchant.R;
import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.universe.Universe;
import com.cs2340.interstellarmerchant.viewmodels.CreateCharacterViewModel;

import java.io.IOException;

/**
 * the starting activity for the game
 */
public class MainActivity extends AppCompatActivity {

    private Spinner difficultySpinner;
    private CreateCharacterViewModel charViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inits the gameController
        GameController gameController = initGameController();
        final Universe universe = gameController.getUniverse();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        difficultySpinner = findViewById(R.id.difficulty_spinner);

        final ArrayAdapter<Difficulty> difficulties = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Difficulty.values());
        difficulties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficulties);

        Button continueBtn = findViewById(R.id.continue_btn);

        // get the edit text for the name edit
        final EditText nameEdit = ((TextInputLayout)
                findViewById(R.id.textInputLayout)).getEditText();
        // store starting text to see if changes have been made later on
        final String startingText = nameEdit.getText().toString();
        charViewModel = ViewModelProviders.of(this).get(CreateCharacterViewModel.class);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    charViewModel.universe = universe;
                    charViewModel.makePlayer(nameEdit.getText().toString().equals(startingText) ? null
                                    : nameEdit.getText().toString(), (Difficulty) difficultySpinner.getSelectedItem(),
                            parseInt(R.id.pilot_skillpts), parseInt(R.id.fighter_skillpts), parseInt(R.id.trader_skillpts),
                            parseInt(R.id.engineer_skillpts));


                    moveToNext();
                } catch (IllegalArgumentException exception) {
                    TextView result = findViewById(R.id.char_setup_result);
                    result.setText(exception.getMessage());
                }

            }
        });
    }

    /**
     * moves to the next screen
     * <p>
     * Throws exception if invalid arguments for creating player
     */
    private void moveToNext()
            throws
            IllegalArgumentException {
        Intent nextActivityIntent = new Intent(MainActivity.this,
                CharacterSummary.class);
        // send the player as a parameter
        nextActivityIntent.putExtra("player", charViewModel.player);
        nextActivityIntent.putExtra("universe", charViewModel.universe);

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

    private GameController initGameController() {
        GameController controller = GameController.getInstance();
        controller.init(createUniverse());
        return controller;
    }
}
