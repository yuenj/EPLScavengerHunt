package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
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
    }

    /**
     * Displays the current question prompt
     */
    private void displayPrompt() {
        TextView prompt = (TextView)findViewById(R.id.question_prompt);
        prompt.setText("Task: " + currentQuestion.getQuestionPrompt());
    }

    /**
     * Displays the view for a mulitple choice question
     *  1. Display Zone, and Prompt
     *  2. Add buttons according to the number of choices in the question
     *  3. Listen for user to press button. When pressed pass on answer to controller.
     */
    private void displayMultChoice(){
        setContentView(R.layout.activity_mult_choice);

        displayZone();
        displayPrompt();

        /* Get the MC choices */
        //TODO grab from supplied question
         final ArrayList<String> choices = new ArrayList<String>();
         choices.add("Hello");
         choices.add("World");
         choices.add("!");

        /* Create choice button(s) */
         for(int i = 0; i < choices.size(); i++) {
             Button mcOption = new Button(this);
             mcOption.setText(choices.get(i));

             LinearLayoutCompat layout = (LinearLayoutCompat) findViewById(R.id.mult_question_layout);
             LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,0f);
             layout.addView(mcOption, parameters);

            /* Listen for button click. If clicked, make a toast telling which button was clicked */
             final int id = i;
             mcOption.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View view) {
                     Toast.makeText(view.getContext(), "Button clicked index = " + id, Toast.LENGTH_SHORT).show();

                     //Pass the answer to the controller
                     qController.requestSubmitResponse(choices.get(id));
                 }
             });
         }
    }

    /**
     *  Ensure that an answer is entered (>0 characters)
     *
     *  If answer is entered:
     *      1.Makes a toast to inform user answer has been received.
     *      2.Gets the user input nad changes to string.
     *      3.Hide the on-screen/ soft keyboard
     *      4.Pass the answer to the controller for further use.
     *
     *  Modified code originally from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     *
     *  @param view, editText
     */
    private void writtenAnswerChecker(View view, EditText editText) {
        TextInputLayout userAnswerLayout = (TextInputLayout) findViewById(R.id.userAnswerWrapper);

        if(editText.getText().length() == 0){
            userAnswerLayout.setError("Answer is too short.");
        } else {
            userAnswerLayout.setErrorEnabled(false);

            Toast.makeText(view.getContext(), "Answer Submitted!", Toast.LENGTH_SHORT).show();

            /* Hides the on-screen (soft) keyboard */
            if (view != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            qController.requestSubmitResponse(editText.getText().toString());
        }
    }

    /**
     * Displays view for a written input question
     *  1. Display zone and prompt text
     *  2. Listen for user input, through the on-screen keyboard send, or by pressing the button
     */
    private void displayWrittenInput(){
        setContentView(R.layout.activity_written_input);

        displayZone();
        displayPrompt();

        /* Modified code whose original is from https://developer.android.com/training/keyboard-input/style.html */
        /* User's keyboard has a send button, which when pressed will submit the answer the user typed in */
        final EditText editText = (EditText) findViewById(R.id.userAnswer);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                /* Answer sent through keyboard send button */
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    writtenAnswerChecker(v, editText);
                    handled = true;
                }

                return handled;
            }
        });

        /* Listens for when button is pressed. When it is pressed, answer is submitted */
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                writtenAnswerChecker(view, editText);
            }
        });
    }

    /**
     * Displays view for a picture input question
     *  1. Display zone and prompt text
     *  2.
     */
    private void displayPicInput(){
        setContentView(R.layout.activity_pic_input);

        displayZone();
        displayPrompt();

        //TODO
    }

    /**
     * Choose which view to display
     * Gets the current question and all info to display the question on user interface
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentQuestion = qController.requestQuestion();

        displayMultChoice();
        //displayWrittenInput();
        //displayPicInput();

        Button skipButton = (Button) findViewById(R.id.skip);

        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Question skipped", Toast.LENGTH_SHORT).show();
                //qController.skip();
            }
        });

    }
}
