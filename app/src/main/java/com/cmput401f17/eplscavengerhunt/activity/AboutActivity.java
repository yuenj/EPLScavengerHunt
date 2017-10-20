package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

/**
 * Contains information about the game and what it is
 * TODO: Figure out what to put on the about screen
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button returnButton = findViewById(R.id.about_return_button);

        // User click leads them to the previous screen
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AboutActivity", "Return Button Pressed");
                Intent intent = new Intent(AboutActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
