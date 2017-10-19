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
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import javax.inject.Inject;

public class TitleActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_title);
        Button startButton = (Button) findViewById(R.id.title_start_button);
        Button rulesButton = (Button) findViewById(R.id.title_rules_button);
        Button aboutButton = (Button) findViewById(R.id.title_about_button);
        Button creditsButton = (Button) findViewById(R.id.title_credits_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "start button pressed");
                Intent intent = new Intent(TitleActivity.this, DebugActivity.class);
                gameController.initScav();
                startActivity(intent);
                finish();
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "rules button pressed");
                Intent intent = new Intent(TitleActivity.this, RulesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "about button pressed");
                Intent intent = new Intent(TitleActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "credits button pressed");
                Intent intent = new Intent(TitleActivity.this, CreditsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Checks permissions, specifically bluetooth, for location beaocnss
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }
}

