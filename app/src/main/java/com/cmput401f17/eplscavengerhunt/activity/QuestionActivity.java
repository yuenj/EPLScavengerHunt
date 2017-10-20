package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Displays the current zone and question for the user.
 * The user is prompted to answer depending on the type of question
 * When the user responds, the activity routes them to LocationActivity
 */
public class QuestionActivity extends AppCompatActivity {

    @Inject
    QuestionController qController;

    @Inject
    GameController gameController;

    @Inject
    LocationController locationController;

    private Question currentQuestion;

    /**
     * Displays the current zone
     */
    private void displayZone() {
        TextView zone = findViewById(R.id.zone);
        zone.setText(getString(R.string.current_zone, locationController.requestZone().getName()));
    }

    /**
     * Displays the current question prompt
     */
    private void displayPrompt() {
        TextView prompt = findViewById(R.id.question_prompt);
        prompt.setText(getString(R.string.question_prompt, currentQuestion.getQuestionPrompt()));
    }

    /**
     * Displays the view for a multiple choice question
     * 1. Display Zone, and Prompt
     * 2. Add buttons according to the number of choices in the question
     * 3. Listen for user to press button. When pressed pass on answer to controller.
     * 4. Use intentAway to move to next activity
     */
    private void displayMultChoice() {
        setContentView(R.layout.activity_mult_choice);

        displayZone();
        displayPrompt();

        // Get the MC choices
        final ArrayList<String> choices = currentQuestion.getChoices();

        // Create choice button(s)
        for (int i = 0; i < choices.size(); i++) {
            Button mcOption = new Button(this);
            mcOption.setText(choices.get(i));


            LinearLayoutCompat layout = findViewById(R.id.choice_buttons);
            LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            layout.addView(mcOption, parameters);

            /* Listen for button click. If clicked, make a toast telling which button was clicked */
            final int id = i;
            mcOption.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Button clicked index = " + id, Toast.LENGTH_SHORT).show();

                    //Pass the answer to the controller
                    qController.requestSubmitResponse(choices.get(id));
                    intentAway();
                }
            });
        }
    }

    /**
     * Ensure that an answer is entered (>0 characters)
     * <p>
     * If answer is entered:
     * 1.Makes a toast to inform user answer has been received.
     * 2.Gets the user input nad changes to string.
     * 3.Hide the on-screen/ soft keyboard
     * 4.Pass the answer to the controller for further use.
     * 5.Use intentAway to move to next activity
     * <p>
     * Modified code originally from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     * <p>
     * // TODO: Add javadoc descriptions for these parameters
     *
     * @param view
     * @param editText
     */
    private void writtenAnswerChecker(View view, EditText editText) {
        TextInputLayout userAnswerLayout = findViewById(R.id.userAnswerWrapper);

        if (editText.getText().length() == 0) {
            userAnswerLayout.setError("Answer is too short.");
        } else {
            userAnswerLayout.setErrorEnabled(false);

            Toast.makeText(view.getContext(), "Answer Submitted!", Toast.LENGTH_SHORT).show();

            // Hides the on-screen (soft) keyboard
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            qController.requestSubmitResponse(editText.getText().toString());
            intentAway();
        }
    }

    /**
     * Displays view for a written input question
     * 1. Display zone and prompt text
     * 2. Listen for user input, through the on-screen keyboard send, or by pressing the button
     */
    private void displayWrittenInput() {
        setContentView(R.layout.activity_written_input);

        displayZone();
        displayPrompt();

        /* Modified code whose original is from https://developer.android.com/training/keyboard-input/style.html */
        /* User's keyboard has a send button, which when pressed will submit the answer the user typed in */
        final EditText editText = findViewById(R.id.userAnswer);
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
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                writtenAnswerChecker(view, editText);
            }
        });
    }

    /**
     * Displays view for a picture input question
     * 1. Display zone and prompt text
     * 2.
     */
    private void displayPicInput() {
        setContentView(R.layout.activity_pic_input);

        displayZone();
        displayPrompt();

        // TODO: Link the button to the camera and display a photo once taken

        // Display the choices
        final ArrayList<String> choices = currentQuestion.getChoices();

        // Create group for radio buttons
        RadioGroup rg = new RadioGroup(this);
        rg.setOrientation(LinearLayout.VERTICAL);

        // Create choice radio button(s)
        for (int i = 0; i < choices.size(); i++) {
            final RadioButton radio = new RadioButton(this);
            radio.setId(i);
            radio.setText(choices.get(i));

            rg.addView(radio);

            // Listen for a radio button to be selected.
            radio.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "You selected: " + radio.getText().toString(), Toast.LENGTH_SHORT).show();

                    //Pass the answer to the controller
                    qController.requestSubmitResponse(radio.getText().toString());
                    intentAway();
                }
            });
        }

        /* Add the radio button group to the view */
        LinearLayoutCompat layout = findViewById(R.id.pic_choice_layout);
        LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layout.addView(rg, parameters);
    }


    /**
     * Used to intent to the next activity
     * Need to increase the Scavenger Hunt stage before we leave the activity to advance the game.
     * If this is the last question we need to intent to the Congrats Activity
     * If this is not the last question we need to intent to the
     */
    private void intentAway() {
        if (!gameController.requestCheckGameOver()) {
            gameController.requestIncrementCurrentStage();
            Intent intent = new Intent(QuestionActivity.this, LocationActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(QuestionActivity.this, CongratulationsActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * Choose which view to display
     * Gets the current question and all info to display the question on user interface
     * Determines type of question and how to display based on aspects of the question
     * Also handles user skipping a question
     * 1. Question is set to skipped
     * 2. Passes a blank response to question controller
     * 3. Moves to next activity using intent away
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        currentQuestion = qController.requestQuestion();


        //TODO Conditional for choosing the view that is less hacky
        if (currentQuestion.isChoicesEmpty())
            displayWrittenInput();
        else {
            displayMultChoice();
        }

        //displayPicInput();


        /* Skip button on all view */
        Button skipButton = findViewById(R.id.skip);

        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Question skipped", Toast.LENGTH_SHORT).show();
                qController.skip(currentQuestion);
                qController.requestSubmitResponse("");
                intentAway();

            }
        });

    }
}
