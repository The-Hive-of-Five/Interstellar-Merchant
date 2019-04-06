package com.cs2340.interstellarmerchant.model.repository;

import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;

import java.util.Collection;

/**
 * Represents a database for saves
 */
public interface Database {

    /**
     * Deletes saves with the saveName
     * @param saveName - the saveName
     * @return whether a save was deleted
     */
    boolean deleteSave(String saveName);

    /**
     * Get the available save overviews (name and last date modified)
     * @return the save
     */
    Collection<SaveOverview> getSaveOverviews();

    /**
     * Get the save with the save name.
     * @param saveName - the save name
     *
     * @throws IllegalArgumentException when the save cannot be found
     *
     * @return - the save state
     */
    SaveState getSave(String saveName) throws IllegalArgumentException;

    /**
     * Stores the save in the repository. Deletes existing saves in the repository that
     * already have the name
     *
     * @param save - the save
     */
    void storeSave(SaveState save);
}
