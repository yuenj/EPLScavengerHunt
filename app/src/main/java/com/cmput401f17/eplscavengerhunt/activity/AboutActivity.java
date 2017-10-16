package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmput401f17.eplscavengerhunt.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button returnButton = (Button) findViewById(R.id.about_return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, TitleActivity.class);
                startActivity(intent);
            }
        });
    }
}
