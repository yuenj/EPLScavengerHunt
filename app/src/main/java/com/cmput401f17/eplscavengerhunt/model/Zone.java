package com.cmput401f17.eplscavengerhunt.model;

/**
 * Beacon ids are specific to each estimote beacon and are set by estimote.
 * You need to go to estimote.cloud.com to see a given beacons ID.
 */

public class Zone {
    private String beaconID;

    public Zone(String beaconID) {
        this.beaconID = beaconID;
    }

    public String getBeaconID() {
        return beaconID;
    }
}
