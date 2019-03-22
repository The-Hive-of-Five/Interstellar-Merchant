package com.cs2340.interstellarmerchant.model.repository;

import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;

import java.util.Collection;

public class MockDatabase implements Database {
    @Override
    public boolean deleteSave(String saveName) {
        return false;
    }

    @Override
    public SaveState getSave(String saveName) {
        return null;
    }

    @Override
    public void storeSave(SaveState save) {

    }

    @Override
    public Collection<SaveOverview> getAvailableSaves() {
        return null;
    }
}
