package com.cmput401f17.eplscavengerhunt.controller;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.custom.SimpleCallback;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.service.BeaconManager;

import javax.inject.Inject;

/**
 * LocationController performs player zone detection and fetches the current zone.
 */
public class LocationController {

    private final ScavHuntState scavHuntState;
    private final BeaconManager beaconManager;

    @Inject
    public LocationController(final ScavHuntState scavHuntState) {
        Context appContext = ScavengerHuntApplication.getInstance();
        this.scavHuntState = scavHuntState;
        beaconManager = new BeaconManager(appContext);
    }

    /**
     * Starts beacon discovery.
     * We call this method multiple times to
     * restart beacon discovery after it has ended.
     */
    public void startDiscovery() {
        // Connect to scanning service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {

            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }

        });
    }

    /**
     * Sets a listener for proximity to see if the user has
     * entered the zone of the current beacon
     * Taken from https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
     */
    public void verifyLocation(@NonNull final SimpleCallback<Boolean> finishedCallback) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beaconManager.stopLocationDiscovery();
                finishedCallback.callback(true);
            }
        }, 1000);

        // Comment when not testing
        /*
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {

            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);

                for (EstimoteLocation beacon : beacons) {
                    // If the user is close or very close to the beacon
                    // we stop discovery and return true via callback
                    if (beacon.id.toString().equals(requestZone().getBeaconID()) &&
                            (RegionUtils.computeProximity(beacon) == Proximity.NEAR ||
                                    RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE)) {
                        Log.d("LocationListener", "Found Beacon");
                        beaconManager.stopLocationDiscovery();
                        finishedCallback.callback(true);
                    }
                }

            }

        }); */
    }

    /**
     * Requests the next zone along the zone route
     */
    public Zone requestZone() {
        final int currentStage = scavHuntState.getCurrentStage();
        Log.d("DEBUG", "Current stage: " + Integer.toString(currentStage));
        return scavHuntState.getZoneRoute().get(currentStage);
    }
}