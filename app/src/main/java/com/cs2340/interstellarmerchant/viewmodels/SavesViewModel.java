package com.cs2340.interstellarmerchant.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cs2340.interstellarmerchant.model.GameController;
import com.cs2340.interstellarmerchant.model.repository.Database;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
import com.cs2340.interstellarmerchant.views.LoadRecyclerViewAdapter;
import com.cs2340.interstellarmerchant.views.SaveRecyclerViewAdapter;

import java.util.Collection;

public class SavesViewModel extends AndroidViewModel {

    public GameController gc;
    public Database db;
    public SaveRecyclerViewAdapter saveAdapter;
    public LoadRecyclerViewAdapter loadAdapter;

    public SavesViewModel(@NonNull Application application) {
        super(application);

        gc = GameController.getInstance();
        db = gc.getDatabase();
    }

    public Collection<SaveOverview> getSaveList() {
        return db.getSaveOverviews();
    }

    public void saveGame(String name) {
        gc.getDatabase().storeSave(new SaveState(gc.getPlayer(), gc.getUniverse(), gc.getTimeController(), name));
    }

    public void updateSaves() {
        db = gc.getDatabase();
        saveAdapter.names.clear();
        saveAdapter.times.clear();
        for (SaveOverview so : getSaveList()) {
            saveAdapter.names.add(so.getName());
            saveAdapter.times.add(so.getLastModified().toString());
        }
        saveAdapter.notifyDataSetChanged();
    }

    public void loadGame(String name) {
        SaveState saveState = db.getSave(name);
        GameController.clearGameController();
        gc = GameController.getInstance();
        gc.init(saveState);
        gc = GameController.getInstance();
        db = gc.getDatabase();
        loadAdapter.names.clear();
        loadAdapter.times.clear();
        for (SaveOverview so : getSaveList()) {
            loadAdapter.names.add(so.getName());
            loadAdapter.times.add(so.getLastModified().toString());
        }
        loadAdapter.notifyDataSetChanged();
    }
}
