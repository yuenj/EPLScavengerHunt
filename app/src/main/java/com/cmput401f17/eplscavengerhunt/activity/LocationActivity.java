package com.cmput401f17.eplscavengerhunt.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.estimote.coresdk.cloud.model.Device;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

import javax.inject.Inject;

public class LocationActivity extends AppCompatActivity {
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    } */

    public LocationController locationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationController = new LocationController(getApplicationContext());
        locationController.VerifyLocation();
    }

    /*
    private boolean notificationAlreadyShown = false;

    // 1. Create the beacon manager
    BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        EstimoteSDK.initialize(getApplicationContext(), "eplscavengerhunt-1w7", "873d67b3977902a51b8d172bdba0a94c");
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);

                // Square brackets because beacon.id.toString() parses with square brackets
                String beaconId = "[ab1d6643c33e5f6ed7c52a062168f137]";

                for (EstimoteLocation beacon : beacons) {
                    if (beacon.id.toString().equals(beaconId) &&
                            (RegionUtils.computeProximity(beacon) == Proximity.IMMEDIATE ||
                             RegionUtils.computeProximity(beacon) == Proximity.NEAR)) {
                        showNotification("Hello world", "Looks like you're near a beacon.");
                        Log.d("LocationListener", "We found it");
                    }
                }
            }
        });

        // 2. Initialize the beacon manager by connecting to the scanning service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    // 3. Disconnect from the scanning service
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }

    public void showNotification(String title, String message) {
        if (notificationAlreadyShown) { return; }

        Log.d("LocationListener", "notify");

        Intent notifyIntent = new Intent(this, LocationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LocationActivity.class);
        stackBuilder.addNextIntent(notifyIntent);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The id of the channel.
        String id = "my_channel_01";
        CharSequence name = "hello123";
        String description = "testcase";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setChannelId(id);

        notificationManager.notify(1, notification.build());
        notificationAlreadyShown = false;
    } */
}
