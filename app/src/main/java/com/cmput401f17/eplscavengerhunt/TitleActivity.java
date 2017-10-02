package com.cmput401f17.eplscavengerhunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Button startButton = (Button) findViewById(R.id.title_start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TitleActivity", "start button pressed");
                Intent intent = new Intent(TitleActivity.this, DebugActivity.class);
                startActivity(intent);
            }
        });
    }
}

