package com.cmput401f17.eplscavengerhunt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper;
import com.estimote.coresdk.service.BeaconService;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Nathan on 2017-10-06.
 */

public class LocationControllerTest {
    @Inject
    ScavHuntState scavHuntState;
    //ScavHuntState scavHuntState = new ScavHuntState();

    LocationController locationController;

    // Test obviously doesn't work
    // I'm not sure how to actually test this component
    // TODO: Make a working test
    @Test
    public void verifyLocationTest() {
        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]"); // DJBeet
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]"); // Candystore
        testZoneRoute.add(zone1);
        testZoneRoute.add(zone2);
        scavHuntState.setZoneRoute(testZoneRoute);

        Context fakeContext = mock(Context.class, RETURNS_DEEP_STUBS);

        locationController = new LocationController(fakeContext);

        // If the location is verified go to Question activity
        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                // This doesn't break the test apparently
                assertTrue(false);
                assertEquals(data, true);
                assertEquals(data, false);
                assertEquals(data, "00000001234234");
            }
        });
    }

    // I tried dependency injection with dagger 2 but
    // there's a null pointer exception each time
    // TODO: Properly inject the singleton scavHuntState
    @Test
    public void requestNextZoneTest() {
        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]"); // DJBeet
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]"); // Candystore
        testZoneRoute.add(zone1);
        testZoneRoute.add(zone2);
        scavHuntState.setZoneRoute(testZoneRoute);
        scavHuntState.setCurrentStage(0);

        Context fakeContext = mock(Context.class, RETURNS_DEEP_STUBS);
        locationController = new LocationController(fakeContext);

        assertEquals(scavHuntState.getCurrentZone(), zone1);
        assertEquals(locationController.requestNextZone(), zone2);
    }
}
