package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;

import javax.inject.Inject;

public class DebugActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        Button goto_QuestionActivity = (Button) findViewById(R.id.goto_questionactivity_button);
        Button goto_LocationActivity = (Button) findViewById(R.id.goto_locationactivity_button);
        Button goto_congratulationsActivity = (Button) findViewById(R.id.goto_congratulationsactivity_button);
        Button goto_resultsActivity = (Button) findViewById(R.id.goto_resultsactivity_button);
        TextView dagger = (TextView) findViewById(R.id.dagger);

        goto_QuestionActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DebugActivity", "going to QuestionActivity");
                Intent intent = new Intent(DebugActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        goto_LocationActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DebugActivity", "going to LocationActivity");
                Intent intent = new Intent(DebugActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        goto_congratulationsActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DebugActivity", "going to CongratulationsActivity");
                Intent intent = new Intent(DebugActivity.this, CongratulationsActivity.class);
                startActivity(intent);
            }
        });

        goto_resultsActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DebugActivity", "going to SummaryActivity");
                Intent intent = new Intent(DebugActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });

         // dagger.setText(gameController.initGame());
        gameController.initScav();
    }
}
