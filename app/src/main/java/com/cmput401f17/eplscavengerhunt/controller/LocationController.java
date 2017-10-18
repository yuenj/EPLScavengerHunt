package com.cmput401f17.eplscavengerhunt.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Location controller handles finding beacons and
 * requesting the prize location
 * It also increments the current stage so the game can
 * know which zone it is on
 */
public class LocationController {
    @Inject
    ScavHuntState scavHuntState;

    private BeaconManager beaconManager;

    /**
     * Instantiates the beacon manager to use beacon technologies
     */
    public LocationController() {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        Context appContext = ScavengerHuntApplication.getInstance();
        beaconManager = new BeaconManager(appContext);
    }

    /**
     * Starts beacon discovery
     */
    public void startDiscovery() {
        // Increment stage so we can look for the next zone
        scavHuntState.incrementCurrentStage();

        // Connect to scanning service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    /**
     * https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
     * Hacky method to get a return value
     * Sets a listener for proximity to see if the user has
     * entered the zone of the current beacon
     * @param finishedCallback
     */
    public void verifyLocation(@NonNull final SimpleCallback<Boolean> finishedCallback) {
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
        });
    }

    /**
     * Requests the next zone from scavHuntState
     * @return
     */
    public Zone requestZone() {
        int currentStage = scavHuntState.getCurrentStage();
        return scavHuntState.getZoneRoute().get(currentStage);
    }
}