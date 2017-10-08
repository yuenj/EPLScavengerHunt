package com.cmput401f17.eplscavengerhunt.activity;

/* Imports  */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_choice);

        QuestionController.requestQuestion()

        /* Get the MC choices */
        choices.add("text");
        choices.add("Ryan");

        /* Create choice button(s) */
        for(int i = 0; i < choices.size(); i++) {
            Button mcOption = new Button(this);
            mcOption.setText(choices.get(i));

            LinearLayoutCompat layout = (LinearLayoutCompat) findViewById(R.id.question_layout);
            LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            layout.addView(mcOption, parameters);

            /* Listen for button click. If clicked, make a toast telling which button was clicked */
            final int id = i;
            mcOption.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Button clicked index = " + id, Toast.LENGTH_SHORT).show();

                    //TODO: Pass response to Question Controller
                }
            });
        }
    }
}
