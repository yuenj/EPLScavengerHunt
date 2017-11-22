package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

/**
 * Contains references to the creators of the app
 * as well as references to any content used in the app
 */
public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        final Button returnButton = findViewById(R.id.credits_return_button);

        // User click leads them to the previous screen
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreditsActivity.this, TitleActivity.class);
                returnButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
