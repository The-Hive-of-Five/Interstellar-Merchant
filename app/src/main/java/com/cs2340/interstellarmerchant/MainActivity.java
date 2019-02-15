package com.example.interstellarmerchant;

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
                EditText pilotSkillPts = (EditText) findViewById(R.id.pilot_skillpts);
                EditText fighterSkillPts = (EditText) findViewById(R.id.fighter_skillpts);
                EditText traderSkillPts = (EditText) findViewById(R.id.trader_skillpts);
                EditText engineerSkillPts = (EditText) findViewById(R.id.engineer_skillpts);
                TextView result = (TextView) findViewById(R.id.char_setup_result);

                int pilotP = Integer.parseInt(pilotSkillPts.getText().toString());
                int fighterP = Integer.parseInt(fighterSkillPts.getText().toString());
                int traderP = Integer.parseInt(traderSkillPts.getText().toString());
                int engineerP = Integer.parseInt(engineerSkillPts.getText().toString());
            }
        });
    }
}
