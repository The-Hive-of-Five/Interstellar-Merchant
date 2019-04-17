package com.cs2340.interstellarmerchant.model;

import com.cs2340.interstellarmerchant.model.repository.DatabaseProvider;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = DatabaseProvider.class)
@Singleton
interface GameControllerMaker {
    GameController get();
}
