package com.cs2340.interstellarmerchant.model.repository;

import com.cs2340.interstellarmerchant.model.repository.Database;
import com.cs2340.interstellarmerchant.model.repository.MongodbDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseProvider {
    @Provides
    @Singleton
    Database provideDatabase() {
        return new MongodbDatabase();
    }
}
