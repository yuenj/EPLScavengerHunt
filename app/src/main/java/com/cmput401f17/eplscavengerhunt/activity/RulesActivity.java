package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;

import javax.inject.Inject;

/**
 * Contains instructions on how to play the game which includes zones and types of questions
 */
public class RulesActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        final Button startButton = findViewById(R.id.rules_start_button);
        final Button returnButton = findViewById(R.id.rules_return_button);

        // User click leads them to the start of the game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this, LocationActivity.class);
                startButton.setEnabled(false);
                gameController.initGame();
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the previous screen
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this, TitleActivity.class);
                returnButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
