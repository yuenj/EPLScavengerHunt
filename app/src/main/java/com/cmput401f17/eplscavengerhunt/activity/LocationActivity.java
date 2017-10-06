package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;
import com.cmput401f17.eplscavengerhunt.model.Zone;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Not sure how data is passed
        // TODO: Update in accordance with intent passing
        // Zone zone = (Zone) getIntent().getSerializableExtra("Object-Name");

        // DJbeet Beacon identifier for testing
        //Zone zone = new Zone("[4f8113396f78d23ec78edfb96c79e23a]");

        LocationController locationController = new LocationController(getApplicationContext());

        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data) {
                    Intent intent = new Intent(LocationActivity.this, QuestionActivity.class);
                    startActivity(intent);
                    finish();
                } else { // This shouldn't go off
                    Log.d("LocationListener", "false return");
                }
            }
        });
    }
}
