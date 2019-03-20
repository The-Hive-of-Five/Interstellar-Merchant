package com.cs2340.interstellarmerchant.model.repository;

import javax.inject.Singleton;

import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
import com.mongodb.client.MongoDatabase;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import org.bson.Document;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Used to interface with whatever backend storage the app uses for saves
 */
@Singleton
public class DatabaseMongo implements Database {
    private static final String APP_ID = "com.cs2340.interstellarmerchant";
    private static final String DATABASE_NAME = "interstellar-merchant";
    private static final String SAVE_COLLECTION = "saves";

    // Create the default Stitch Client
    final StitchAppClient client;
    // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
    final MongoClient mobileClient;
    // Get database
    final MongoDatabase database;
    // Saves collection
    final MongoCollection<Document> savesCollection;

    public DatabaseMongo() {
        // Create the stitch client. Initialize one if it doesn't already exist
        StitchAppClient tempClient;
        try {
            tempClient = Stitch.getAppClient(APP_ID);
        } catch (IllegalStateException exception) {
            tempClient = Stitch.initializeDefaultAppClient(APP_ID);
        }
        client = tempClient;

        // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
        mobileClient = client.getServiceClient(LocalMongoDbService.clientFactory);
        // Get database
        database = mobileClient.getDatabase(DATABASE_NAME);
        // Saves collection
        savesCollection = database.getCollection(SAVE_COLLECTION);

    }

    @Override
    public Collection<SaveOverview> getAvailableSaves() {
        Collection<SaveOverview> saveOverviews = new LinkedList<>();
        for (Document saveDoc: savesCollection.find()) {
            String name = saveDoc.getString("name");
            Date date = saveDoc.getDate("lastModified");
            saveOverviews.add(new SaveOverview(name, date));
        }
        return saveOverviews;
    }

    @Override
    public void storeSave(SaveState save) {
        Document saveDocument = Document.parse(save.getSerialization());
        savesCollection.insertOne(saveDocument);
    }




}
