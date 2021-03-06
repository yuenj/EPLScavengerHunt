package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

/**
 * The About page of the app
 * contains information about the game and what it is
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // find views
        final Button returnButton = findViewById(R.id.about_return_button);

        // set up on click listeners
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, TitleActivity.class);
                returnButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
