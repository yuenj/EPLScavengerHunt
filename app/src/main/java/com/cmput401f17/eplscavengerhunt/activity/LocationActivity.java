package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import javax.inject.Inject;

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
        // If the location is verified go to Question activity
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