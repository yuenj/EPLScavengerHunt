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
 * Contains instructions on how to play the game
 * which includes zones and types of questions
 */
public class RulesActivity extends AppCompatActivity {
    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_rules);

        Button startButton = findViewById(R.id.rules_start_button);
        Button returnButton = findViewById(R.id.rules_return_button);

        // User click leads them to the start of the game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RulesActivity", "Start Button Pressed");
                //Intent intent = new Intent(RulesActivity.this, LocationActivity.class);
                Intent intent = new Intent(RulesActivity.this, DebugActivity.class);
                gameController.initScav();
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to the previous screen
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RulesActivity", "Return Button Pressed");
                Intent intent = new Intent(RulesActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
