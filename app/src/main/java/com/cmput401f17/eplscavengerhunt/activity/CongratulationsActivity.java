package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

/**
 * The Congratulations page of the app
 * shows a congratulatory message when the user completes the scavenger hunt
 */
public class CongratulationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        // find views
        final Button startButton = findViewById(R.id.button_congrats_view_summary);

        // set up on click listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CongratulationsActivity.this, SummaryActivity.class);
                startButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
