package com.cs2340.interstellarmerchant.model.repository;

import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;

import java.util.Collection;

public interface Database {
    /**
     * Get the available saves
     * @return the save
     */
    Collection<SaveOverview> getAvailableSaves();

    /**
     * Stores the save in the repository
     * @param save - the save
     */
    void storeSave(SaveState save);
}
