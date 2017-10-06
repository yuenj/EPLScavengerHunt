package com.cmput401f17.eplscavengerhunt.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class LocationController extends Application {
    private BeaconManager beaconManager;

    public LocationController(Context context) {
        beaconManager = new BeaconManager(context);

        // Connect to scanning service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    // TODO: Change arguments to a Zone
    public int VerifyLocation(String beacon) {
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);

                // TODO: Read string id's from database according to zone
                // Square brackets because beacon.id.toString() parses with square brackets
                String CandyStore = "[ab1d6643c33e5f6ed7c52a062168f137]";
                String DJBeet = "[4f8113396f78d23ec78edfb96c79e23a]";       // Beacon names
                String Lemonade = "[9a78af8c1252fcb37abefecbbbe7322a]";

                for (EstimoteLocation beacon : beacons) {
                    if (beacon.id.toString().equals(beacon) &&
                            (RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE ||
                            RegionUtils.computeProximity(beacon) == Proximity.NEAR)) {
                                Log.d("LocationListener", "Found Beacon");
                    }
                }
            }
        });
        return 0;
    }
}