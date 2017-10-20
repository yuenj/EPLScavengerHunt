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
 * Location controller handles finding beacons
 * and requesting the current zone. It uses estimote api
 * to determine if the user is near to a beacon
 */
public class LocationController {
    private final ScavHuntState scavHuntState;
    private final BeaconManager beaconManager;

    /**
     * Instantiates the beacon manager to use beacon technologies
     */
    @Inject
    public LocationController(ScavHuntState scavHuntState) {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

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
     *
     * https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
     * Hacky method to get a return value
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
     * Requests the next zone along the zone route
     * from the singleton scavHuntState
     * @return Zone         A zone in a branch of a library
     */
    public Zone requestZone() {
        int currentStage = scavHuntState.getCurrentStage();
        return scavHuntState.getZoneRoute().get(currentStage);
    }
}