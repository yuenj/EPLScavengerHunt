package com.cmput401f17.eplscavengerhunt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper;
import com.estimote.coresdk.service.BeaconService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ScavHuntState mockScavHuntState;

    @Captor
    private ArgumentCaptor<List<Zone>> zoneRouteCaptor;


    LocationController locationController;
    private Zone zone1;
    private Zone zone2;
    private Zone zone3;
    private Zone zone4;
    List<Zone> zoneRoute;

    @Before
    public void initLocationTest() {
        locationController = new LocationController(mockScavHuntState);
        zone1 = mock(Zone.class);
        zone2 = mock(Zone.class);
        zone3 = mock(Zone.class);
        zone4 = mock(Zone.class);

        zoneRoute = new ArrayList<>();
        Collections.addAll(zoneRoute, zone1, zone2, zone3, zone4);

        when(mockScavHuntState.getCurrentStage()).thenReturn(2);
        //when(mockScavHuntState.getZoneRoute().get(mockScavHuntState.getCurrentStage())).thenReturn(zone3);
    }

    @Test
    public void requestZoneTest() {

        when(mockScavHuntState.getZoneRoute()).thenReturn(zoneRoute);
        Zone returnedZone = locationController.requestZone();


        assertEquals(zone3, returnedZone);
    }
}
