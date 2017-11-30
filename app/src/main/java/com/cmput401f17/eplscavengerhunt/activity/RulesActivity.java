package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;

import javax.inject.Inject;

/**
 * The Rules page of the app
 * Contains instructions on how to play the game
 */
public class RulesActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        // find views
        final Button startButton = findViewById(R.id.rules_start_button);
        final Button returnButton = findViewById(R.id.rules_return_button);

        // set up on click listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startButton.setEnabled(false);
                Intent intent = new Intent(RulesActivity.this, LocationActivity.class);
                gameController.initGame();
                startActivity(intent);
                finish();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnButton.setEnabled(false);
                Intent intent = new Intent(RulesActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
