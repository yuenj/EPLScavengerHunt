package com.cmput401f17.eplscavengerhunt.model;

/**
 * Beacon ids are specific to each estimote beacon and are set by estimote.
 * You need to go to estimote.cloud.com to see a given beacons ID.
 */
public class Zone {

    private String beaconID;
    private String name;
    private String colour;

    public Zone(String beaconID) {
        this.beaconID = beaconID;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {return colour;}

    public void setColour(String colour) {this.colour = colour;}
}
