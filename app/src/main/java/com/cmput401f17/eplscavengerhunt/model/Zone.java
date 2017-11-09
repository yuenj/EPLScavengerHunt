package com.cmput401f17.eplscavengerhunt.model;

/**
 * Beacon ids are specific to each estimote beacon and are set by estimote.
 * You need to go to estimote.cloud.com to see a given beacons ID.
 */
public class Zone {
    private String beaconID, name, branch, backGrColour;

    public Zone () {
        beaconID = "";
        name = "";
        branch = "";
        backGrColour = "";
    }

    public Zone(String beaconID) {
        this.beaconID = beaconID;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) { this.beaconID = beaconID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() { return branch; }

    public void setBranch(String branch) { this.branch = branch; }

    public String getBackGrColour() {
        return backGrColour;
    }

    public void setBackGrColour(String backGrColour) {
        this.backGrColour = backGrColour;
    }
}
