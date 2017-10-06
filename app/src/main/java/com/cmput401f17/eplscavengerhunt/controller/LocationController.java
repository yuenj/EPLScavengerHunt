package com.cmput401f17.eplscavengerhunt.controller;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

import javax.inject.Inject;

public class LocationController extends Application {

    @Inject
    ScavHuntState scavHuntState;

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

    // https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
    // Hacky method to get a return value
    public void verifyLocation(@NonNull final SimpleCallback<Boolean> finishedCallback) {
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);

                for (EstimoteLocation beacon : beacons) {
                    if (beacon.id.toString().equals(requestNextZone().getBeaconID()) &&
                            RegionUtils.computeProximity(beacon) == Proximity.NEAR ||
                            RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE) {
                        Log.d("LocationListener", "Found Beacon");
                        beaconManager.stopLocationDiscovery();
                        finishedCallback.callback(true);
                    }
                }
            }
        });
    }

    public Zone requestNextZone() {
        scavHuntState.incrementCurrentStage();
        int currentStage = scavHuntState.getCurrentStage();
        return scavHuntState.getZoneRoute().get(currentStage);
    }
}