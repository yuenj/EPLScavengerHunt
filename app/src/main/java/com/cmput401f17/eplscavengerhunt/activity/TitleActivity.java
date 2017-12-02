package com.cmput401f17.eplscavengerhunt.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import javax.inject.Inject;

/**
 * The Title page of the app
 * represents the entry point to the game
 */
public class TitleActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 20;
    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        // find views
        final Button startButton = findViewById(R.id.title_start_button);
        final Button rulesButton = findViewById(R.id.title_rules_button);
        final Button aboutButton = findViewById(R.id.title_about_button);
        final Button creditsButton = findViewById(R.id.title_credits_button);

        // set on click listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, LocationActivity.class);
                // initialize the game state
                startButton.setEnabled(false);
                gameController.initGame();

                if(!checkConnection()) {
                    System.out.println("Database Connection Error. Restarting.");
                    intent = new  Intent(TitleActivity.this, TitleActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    System.out.println("Database Connected!!");
                    startActivity(intent);
                    finish();
                }
            }
        });
        rulesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, RulesActivity.class);
                rulesButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, AboutActivity.class);
                aboutButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
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
     * Checks if the players device is connected to a network
     */
    private boolean checkConnection() {
        Context context = getApplicationContext();
        CharSequence text = "Connection Error. Please ensure that you are connect to a network (WiFi or Data)";
        int duration = Toast.LENGTH_SHORT;

        if (!gameController.requestCheckConnection()) {
            Toast.makeText(context, text, duration).show();
            return false;
        };

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // check bluetooth for location beacons
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        // checks for storage access (for getting fullsize photo taken from camera)
        getPermissions();
    }

    /**
     * Asks for storage access permissions, in order to obtain fullsize photo
     * for displaying in picture input type questions.
     * Taken from https://developer.android.com/training/permissions/requesting.html
     * Accessed 10-24-2017
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
     * Taken from https://developer.android.com/training/permissions/requesting.html
     * Accessed 10-24-2017
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
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

