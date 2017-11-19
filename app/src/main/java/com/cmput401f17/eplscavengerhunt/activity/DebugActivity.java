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

import javax.inject.Inject;

/**
 * Used for testing purposes, especially for instances in which no physical beacon is possessed
 */
public class DebugActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        final Button goto_QuestionActivity = findViewById(R.id.debug_goto_questionactivity_button);
        final Button goto_LocationActivity = findViewById(R.id.debug_goto_locationactivity_button);
        final Button goto_congratulationsActivity = findViewById(R.id.debug_goto_congratulationsactivity_button);
        final Button goto_resultsActivity = findViewById(R.id.debug_goto_resultsactivity_button);

        // User click leads them to LocationActivity
                goto_LocationActivity.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(DebugActivity.this, LocationActivity.class);
                        goto_LocationActivity.setEnabled(false);
                        startActivity(intent);
                        finish();
            }
        });

        // User click leads them to QuestionActivity
        goto_QuestionActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DebugActivity.this, QuestionActivity.class);
                goto_QuestionActivity.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to CongratulationsActivity
        goto_congratulationsActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DebugActivity.this, CongratulationsActivity.class);
                goto_congratulationsActivity.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });

        // User click leads them to SummaryActivity
        goto_resultsActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DebugActivity.this, SummaryActivity.class);
                goto_resultsActivity.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
