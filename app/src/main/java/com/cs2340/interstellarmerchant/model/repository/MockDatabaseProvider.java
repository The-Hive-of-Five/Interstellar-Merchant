package com.cs2340.interstellarmerchant.model.repository;

public class MockDatabaseProvider extends DatabaseProvider {
    @Override
    Database provideDatabase() {
        return new MockDatabase();
    }
}
