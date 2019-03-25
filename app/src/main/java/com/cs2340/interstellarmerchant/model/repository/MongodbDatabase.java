package com.cs2340.interstellarmerchant.model.repository;

import android.util.Log;

import javax.inject.Singleton;

import com.cs2340.interstellarmerchant.model.repository.save_state.SaveOverview;
import com.cs2340.interstellarmerchant.model.repository.save_state.SaveState;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Used to interface with whatever backend storage the app uses for saves
 */
@Singleton
public class MongodbDatabase implements Database {
    private static final String APP_ID = "com.cs2340.interstellarmerchant";
    private static final String DATE_FORMAT = "MMM dd, yyyy hh:mm:ss a";
    private static final String DATABASE_NAME = "interstellar-merchant";
    private static final String SAVE_COLLECTION = "saves";

    // Create the default Stitch Client
    private final StitchAppClient client;
    // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
    private final MongoClient mobileClient;
    // Get database
    private final MongoDatabase database;
    // Saves collection
    private final MongoCollection<Document> savesCollection;

    public MongodbDatabase() {
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
    public boolean deleteSave(String name) {
       DeleteResult result = savesCollection.deleteMany(Filters.eq("name", name));
       return result.getDeletedCount() > 0;
    }

    @Override
    public Collection<SaveOverview> getAvailableSaves() {
        Collection<SaveOverview> saveOverviews = new LinkedList<>();

        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        for (Document saveDoc: savesCollection.find()) {
            String name = saveDoc.getString("name");
            Date date;
            try {
                date = formatter.parse(saveDoc.getString("lastModified"));
            } catch (ParseException exception) {

                throw new IllegalStateException("The lastModified could not be parsed from the save" +
                        "document. This is in an indication that the device database is broken. " +
                        "Date: " + saveDoc.getString("lastModified"));
            }

            saveOverviews.add(new SaveOverview(name, date));
        }
        return saveOverviews;
    }

    @Override
    public SaveState getSave(String name) {
        Document output = savesCollection.find(Filters.eq("name", name)).first();
        if (output == null) {
            throw new IllegalArgumentException("The save could not be found");
        }
        return SaveState.saveJSONFactory(output.toJson());

    }

    @Override
    public void storeSave(SaveState save) {
        deleteSave(save.name);
        Document saveDocument = Document.parse(save.getSerialization());
        savesCollection.insertOne(saveDocument);
    }
}
