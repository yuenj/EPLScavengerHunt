package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                final boolean success = gameController.initGame();
                if (success) {
                    Log.i("INFO","Successfully initialized the game state");
                    Intent intent = new Intent(RulesActivity.this, LocationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("ERROR", "Could not initialize the game state");
                    Context context = getApplicationContext();
                    CharSequence text = "Connection Error. Please ensure that you are connected to a network (WiFi or Data)";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    // restart the activity
                    Intent intent = new Intent(RulesActivity.this, RulesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0); // cancel the animation between activity transitions
                    finish();
                }
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
