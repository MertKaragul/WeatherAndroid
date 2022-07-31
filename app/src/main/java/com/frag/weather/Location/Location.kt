package com.frag.weather.Location

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

class Location @Inject constructor(){
    private val TAG = "Location"
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private val looper = Looper.getMainLooper()
    private var locationMap : MutableMap<String , Double> = mutableMapOf()
    var mutableLocations = MutableLiveData<MutableMap<String , Double>>()
    fun getLocationUpdate(context : Context , locationReq : Int){
        fusedLocationProviderClient = FusedLocationProviderClient(context)
        if (locationReq == 0){
            locationRequest = LocationRequest.create().apply {
                interval = 1000
                fastestInterval = 5000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }
        }else if(locationReq == 1){
            locationRequest = LocationRequest.create().apply {
                interval = 1000
                fastestInterval = 5000
                priority = Priority.PRIORITY_LOW_POWER
            }
        }else {
            Log.e(TAG , "Not get location Priority")
        }




        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                for (locations in p0.locations){
                    mutableLocations.postValue(mutableMapOf("0" to locations.latitude , "1" to locations.longitude))
                    getLocation()
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , looper)
    }

    fun getLocation() : MutableMap<String , Double>{
        return locationMap
    }

    fun stopLocationListening(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}