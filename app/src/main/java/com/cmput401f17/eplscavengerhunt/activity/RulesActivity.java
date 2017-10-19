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
import com.google.android.gms.games.Game;

import javax.inject.Inject;

public class RulesActivity extends AppCompatActivity {
    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        setContentView(R.layout.activity_rules);
        Button startButton = (Button) findViewById(R.id.rules_start_button);
        Button returnButton = (Button) findViewById(R.id.rules_return_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RulesActivity", "start button pressed");
                Intent intent = new Intent(RulesActivity.this, DebugActivity.class);
                gameController.initScav();
                startActivity(intent);
                finish();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RulesActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
