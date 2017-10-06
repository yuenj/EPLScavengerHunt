package com.cmput401f17.eplscavengerhunt.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

public class LocationController extends Application {
    private BeaconManager beaconManager;
    public boolean result;

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

    public boolean VerifyLocation(final Zone zone) {
        result = false;
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);

                for (EstimoteLocation beacon : beacons) {
                    if (beacon.id.toString().equals(zone.getBeaconID()) &&
                            RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE &&
                            RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE) {
                        Log.d("LocationListener", "Found Beacon");
                        beaconManager.stopLocationDiscovery();
                        result = true;
                        break;
                    }
                }
            }
        });
        if (result) {
            return true;
        } else { // This shouldn't fire
            Log.d("LocationListener", "returned false??");
            return false;
        }
    }
}