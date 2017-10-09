package com.cmput401f17.eplscavengerhunt.activity;

/* Imports  */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.model.MultChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;


import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private QuestionController qController;
    private Question currentQuestion;

    public QuestionActivity() {
        qController = new QuestionController();
        currentQuestion = new Question();
    }

    /**
     * Displays the current zone
     */
    private void displayZone() {
        TextView zone = (TextView)findViewById(R.id.zone);
        zone.setText("Zone: " + qController.requestZone());
        zone.setTextColor(Color.RED);
    }

    /**
     * Displays the current question prompt
     */
    private void displayPrompt() {
        TextView prompt = (TextView)findViewById(R.id.question_prompt);
        prompt.setText("Task: " + currentQuestion.getQuestionPrompt());
    }

    private void displayMultChoice(){
        setContentView(R.layout.activity_mult_choice);

        displayZone();
        displayPrompt();

        /* Get the MC choices */
         ArrayList<String> choices = new ArrayList<String>();
         choices.add("Hello");
         choices.add("World");
         choices.add("!");

        /* Create choice button(s) */
         for(int i = 0; i < choices.size(); i++) {
             Button mcOption = new Button(this);
             mcOption.setText(choices.get(i));

             LinearLayoutCompat layout = (LinearLayoutCompat) findViewById(R.id.mult_question_layout);
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

    private void displayWrittenInput(){
        setContentView(R.layout.activity_written_input);

        displayZone();
        displayPrompt();
    }

    private void displayPicInput(){
        setContentView(R.layout.activity_pic_input);

        displayZone();
        displayPrompt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentQuestion = qController.requestQuestion();

        displayMultChoice();
        //displayWrittenInput();
        //displayPicInput();

    }
}
