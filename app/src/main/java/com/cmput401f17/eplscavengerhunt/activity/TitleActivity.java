package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_title);

        Button startButton = findViewById(R.id.title_start_button);
        Button rulesButton = findViewById(R.id.title_rules_button);
        Button aboutButton = findViewById(R.id.title_about_button);
        Button creditsButton = findViewById(R.id.title_credits_button);

        // User click leads them to the start of the game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "Start Button Pressed");
                //Intent intent = new Intent(RulesActivity.this, LocationActivity.class);
                Intent intent = new Intent(TitleActivity.this, DebugActivity.class);
                gameController.initScav();
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the rules screen
        rulesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "Rules Button Pressed");
                Intent intent = new Intent(TitleActivity.this, RulesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the about screen
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "About Button Pressed");
                Intent intent = new Intent(TitleActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the credits screen
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "Credits Button Pressed");
                Intent intent = new Intent(TitleActivity.this, CreditsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Checks permissions, specifically bluetooth, for location beacons
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }
}

