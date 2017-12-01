package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;

import javax.inject.Inject;

import mbanje.kurt.fabbutton.FabButton;

/**
 * The Location page of the app
 * Indicates the next zone for the player and
 * polls to check if the player has arrived in the zone
 */
public class LocationActivity extends AppCompatActivity {
    @Inject
    LocationController locationController;

    private FabButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        button = findViewById(R.id.location_loading_indicator_fab);
        button.showProgress(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // poll the players location
        locationController.startDiscovery();

        // find views
        final TextView messageTextView = findViewById(R.id.location_next_zone_text_view);
        message.setText("Go to " + locationController.requestZone().getName() + "!");
        final CardView locationCardView = findViewById(R.id.card_view_location);

        // set the theme to match the zone color
        final int zoneColor = Color.parseColor(locationController.requestZone().getColor());
        locationCardView.setCardBackgroundColor(zoneColor);
        button.setColor(zoneColor);

        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                // player is inside the new zone
                if (data) {
                    final Intent intent = new Intent(LocationActivity.this, QuestionActivity.class);

                    // display a checkmark
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

                }
                // TODO: Figure out what to do here if it ever happens
                else {
                    Log.d("LocationListener", "False Return");
                }
            }
        });
    }
}