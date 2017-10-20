package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;

import javax.inject.Inject;

/**
 * Tells the user to go to their next zone
 * Verifies if they are in the zone and routes them
 * to question activity
 */
public class LocationActivity extends AppCompatActivity {
    @Inject
    LocationController locationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_location);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationController.startDiscovery();

        // I've put the message setting here because we need to call requestZone only after
        // the current stage has been incremented from -1 to 0
        TextView message = findViewById(R.id.nextZone);
        message.setText("Go to Zone " + locationController.requestZone().getName() + "!");

        // If the location is verified go to Question activity
        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data) {
                    Intent intent = new Intent(LocationActivity.this, QuestionActivity.class);
                    startActivity(intent);
                    finish();
                } else { // This shouldn't go off
                    Log.d("LocationListener", "False Return");
                }
            }
        });
    }
}