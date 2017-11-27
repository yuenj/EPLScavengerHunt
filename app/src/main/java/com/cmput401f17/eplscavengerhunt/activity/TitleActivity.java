package com.cmput401f17.eplscavengerhunt.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import javax.inject.Inject;

/**
 * Contains the start of the app with options:
 * start, about, credits and rules which can lead to
 * different screens
 */
public class TitleActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        //gameController.initGame();
        setContentView(R.layout.activity_title);
        final Button startButton = findViewById(R.id.title_start_button);
        final Button rulesButton = findViewById(R.id.title_rules_button);
        final Button aboutButton = findViewById(R.id.title_about_button);
        final Button creditsButton = findViewById(R.id.title_credits_button);

        // User click leads them to the start of the game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, LocationActivity.class);
                gameController.initGame();

                startButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the rules screen
        rulesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, RulesActivity.class);
                rulesButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the about screen
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, AboutActivity.class);
                aboutButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the credits screen
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, CreditsActivity.class);
                creditsButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Checks permissions:
     * - Estimote's of permission checker: checks bluetooth, for location beacons
     * - self implementation: checks for storage access (for getting fullsize photo
     *                        taken from camera
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Estimote provided permission checker
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        // self implemented permission checker
        getPermissions();;
    }

    /**
     * Asks for storage access permissions, which is needed for
     * picture input type questions in order to obtain fullsize photo
     * for displaying.
     * reference from https://developer.android.com/training/permissions/requesting.html
     * accessed 10-24-2017
     */
    private void getPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
    /**
     * responds to permissions request result
     * reference from https://developer.android.com/training/permissions/requesting.html
     * accessed 10-24-2017
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

