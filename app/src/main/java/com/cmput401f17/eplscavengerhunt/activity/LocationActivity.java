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

    private FabButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_location);



        button = findViewById(R.id.location_loading_indicator_fab);
        button.showProgress(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationController.startDiscovery();

        // I've put the message setting here because we need to call requestZone only after
        // the current stage has been incremented from -1 to 0
        TextView message = findViewById(R.id.location_next_zone_text_view);
        message.setText("Go to " + locationController.requestZone().getName() + "!");

        //Set Card colour of activity to be zone specific colour



        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(locationController.requestZone().getColor()));


        // If the location is verified go to Question activity
        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data) {
                    final Intent intent = new Intent(LocationActivity.this, QuestionActivity.class);

                    // Switches to a check mark to show that the user has
                    // found the zone
                    button.onProgressCompleted();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Change the colour after 150ms
                            button.setColor(Color.parseColor("#ffa726"));
                        }
                    }, 150);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Switch to the activity after 7000ms
                            startActivity(intent);
                            finish();
                        }
                    }, 700);

                } else { // This shouldn't go off
                    Log.d("LocationListener", "False Return");
                }
            }
        });
    }
}