package com.cs2340.interstellarmerchant.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cs2340.interstellarmerchant.model.player.Player;
import com.cs2340.interstellarmerchant.model.player.game_config.Difficulty;
import com.cs2340.interstellarmerchant.model.player.game_config.GameConfig;
import com.cs2340.interstellarmerchant.model.universe.Universe;

public class CreateCharacterViewModel extends AndroidViewModel {
    public String name = null;
    public GameConfig config = null;
    public int pilotSkill = 0;
    public int fighterSkill = 0;
    public int traderSkill = 0;
    public int engineerSkill = 0;
    public Player player = null;
    public Universe universe;

    public CreateCharacterViewModel(@NonNull Application application) {
        super(application);
    }
    public void makePlayer(String name, Difficulty dif, int pilotSkill, int fighterSkill, int traderSkill, int engineerSkill) {
        this.pilotSkill = pilotSkill;
        this.fighterSkill = fighterSkill;
        this.traderSkill = traderSkill;
        this.engineerSkill = engineerSkill;
        this. config = new GameConfig(dif);
        Player player = Player.getInstance();
        player.init(pilotSkill, fighterSkill, traderSkill, engineerSkill, name,
                this.config);
        player.setLocation((universe.getSystems()[0]).getPlanets().get(0));
        this.player = player;
    }




}
