package com.cs2340.interstellarmerchant.utilities

import android.app.Activity
import android.util.Log
import com.cs2340.interstellarmerchant.BuildConfig
import com.cs2340.interstellarmerchant.model.universe.market.Market
import com.cs2340.interstellarmerchant.model.universe.planet.PlanetEconomy

fun Market.logd(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}
fun PlanetEconomy.logd(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}