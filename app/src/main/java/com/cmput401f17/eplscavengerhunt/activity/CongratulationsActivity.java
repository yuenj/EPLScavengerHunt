package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

/**
 * Shows a congratulatory message when the user is finished and directs them to a summary screen
 */
public class CongratulationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        Button startButton = findViewById(R.id.congrats_results_button);

        // User click leads to summary activity - a summary to their game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Congrats Activity", "Request Summary Button Pressed");
                Intent intent = new Intent(CongratulationsActivity.this, SummaryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
