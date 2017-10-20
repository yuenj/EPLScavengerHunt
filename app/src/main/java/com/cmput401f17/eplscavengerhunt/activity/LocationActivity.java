package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;

import javax.inject.Inject;

import mbanje.kurt.fabbutton.FabButton;

/**
 * Tells the user to go to their next zone
 * Verifies if they are in the zone and routes them
 * to question activity
 */
public class LocationActivity extends AppCompatActivity {
    @Inject
    LocationController locationController;

    FabButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_location);

        button = (FabButton) findViewById(R.id.loading_indicator);
        button.showProgress(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationController.startDiscovery();

        // I've put the message setting here because we need to call requestZone only after
        // the current stage has been incremented from -1 to 0
        TextView message = (TextView) findViewById(R.id.nextZone);
        message.setText("Go to Zone " + locationController.requestZone().getName().toString() + "!");

        // If the location is verified go to Question activity
        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data) {
                    final Intent intent = new Intent(LocationActivity.this, QuestionActivity.class);

                    // Switches to a checkmark photo to show that the user has
                    // found the zone
                    button.onProgressCompleted();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Switch to the activity after 150ms
                            button.setColor(Color.parseColor("#ffa726"));
                        }
                    }, 150);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Switch to the activity after 1000ms
                            startActivity(intent);
                            finish();
                        }
                    }, 700);

                } else { // This shouldn't go off
                    Log.d("LocationListener", "false return");
                }
            }
        });
    }
}