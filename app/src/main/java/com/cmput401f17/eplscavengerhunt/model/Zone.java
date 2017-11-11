package com.cmput401f17.eplscavengerhunt.model;

/**
 * Beacon ids are specific to each estimote beacon and are set by estimote.
 * You need to go to estimote.cloud.com to see a given beacons ID.
 */
public class Zone {
    private String beaconID, name, branch, backGrColour, color, area;

    public Zone () {
        beaconID = "";
        name = "";
        area = "";
        branch = "";
        backGrColour = "";
        color = "";
    }

    public Zone(final String beaconID, final String name, final String area) {
        this.beaconID = beaconID;
        this.name = name;
        this.area = area;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) { this.beaconID = beaconID; }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
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

    public String getArea() {
        return area;
    }

    public void setArea(final String area) { this.area = area; }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }
}
