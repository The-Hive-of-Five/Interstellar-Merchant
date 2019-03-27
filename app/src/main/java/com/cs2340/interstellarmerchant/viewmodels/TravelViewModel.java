package com.cs2340.interstellarmerchant.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.travel.TravelController;
import com.cs2340.interstellarmerchant.model.universe.Universe;

import java.util.ArrayList;
import java.util.Arrays;

public class TravelViewModel extends AndroidViewModel {

    private GameController gc;
    private Universe universe;
    private ArrayList<String> solarSystemList;
    public ArrayList<String> planetList;
    private TravelController tc;

    public TravelViewModel(@NonNull Application application) {
        super(application);
        gc = GameController.getInstance();
        universe = gc.getUniverse();
        tc = gc.getTravelController();
        String[] ssList = new String[universe.getSystems().length];
        for (int i = 0; i < ssList.length; i++) {
            ssList[i] = universe.getSystems()[i].getName();
        }
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(ssList));
        solarSystemList = arrayList;
        planetList = getPlanets(0);
    }


    public ArrayList<String> solarSystemList() {
        return solarSystemList;
    }

    public Universe getUniverse() {
        return universe;
    }

    public ArrayList<String> getPlanets(int index) {
        ArrayList<String> planets = new ArrayList<>();
        int planetListSize = universe.getSystems()[index].getPlanets().size();
        for (int i = 0; i < planetListSize; i++) {
            planets.add(universe.getSystems()[index].getPlanets().get(i).getName());
        }
        planetList = planets;
        return planets;
    }

    public void travel(String ss, String p) {
        int ssIndex = solarSystemList.indexOf(ss);
        int planetIndex = planetList.indexOf(p);
        Log.d("click", "Traveling to Solar System " + universe.getSystems()[ssIndex]);
        Log.d("click", "Traveling to planet " + universe.getSystems()[ssIndex].getPlanets().get(planetIndex));
        tc.travel(universe.getSystems()[ssIndex].getPlanets().get(planetIndex),
                gc.getTimeController());
    }


}





