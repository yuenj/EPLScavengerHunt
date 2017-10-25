package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.custom.CameraHandler;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

public class QuestionActivity extends AppCompatActivity {

    @Inject
    QuestionController questionController;

    @Inject
    GameController gameController;

    @Inject
    LocationController locationController;

    private Question currentQuestion;

    // Attributes related to user picture input
    private ImageView picTakenImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File imageFile;
    CameraHandler cameraHandler = new CameraHandler(this);

    /**
     * Displays the current zone
     */
    private void displayZone() {
        TextView zoneView = (TextView)findViewById(R.id.question_zone_text_view);
        zoneView.setText("Zone: " + locationController.requestZone().getName());
    }

    /**
     * Displays the current question prompt
     */
    private void displayPrompt() {
        TextView prompt = (TextView)findViewById(R.id.question_prompt_text_view);
        prompt.setText("Task: " + currentQuestion.getQuestionPrompt());
    }

    /**
     * Displays the view for a mulitple choice question
     *  1. Display Zone, and Prompt
     *  2. Add buttons according to the number of choices in the question
     *  3. Listen for user to press button. When pressed pass on answer to controller.
     *  4. Use intentAway to move to next activity
     */
    private void displayMultChoice(){
        setContentView(R.layout.activity_mult_choice);

        displayZone();
        displayPrompt();

        /* Get the MC choices */
         final ArrayList<String> choices = currentQuestion.getChoices();

        /* Create choice button(s) */
        for(int i = 0; i < choices.size(); i++) {
            Button mcOption = new Button(this);
            mcOption.setText(choices.get(i));

            LinearLayoutCompat layout = (LinearLayoutCompat) findViewById(R.id.mult_choices_llc);
            LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            layout.addView(mcOption, parameters);

            /* Listen for button click. If clicked, make a toast telling which button was clicked */
             final int id = i;
             mcOption.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View view) {
                     Toast.makeText(view.getContext(), "Button clicked index = " + id, Toast.LENGTH_SHORT).show();

                     //Pass the answer to the controller
                     questionController.requestSubmitResponse(choices.get(id));
                     intentAway();
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
     *      5.Use intentAway to move to next activity
     *
     *  Modified code originally from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     *
     *  @param view, editText
     */
    private void writtenAnswerChecker(View view, EditText editText) {
        TextInputLayout userAnswerLayout = (TextInputLayout) findViewById(R.id.written_user_answer_wrapper_til);

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

            questionController.requestSubmitResponse(editText.getText().toString());
            intentAway();
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
        final EditText editText = (EditText) findViewById(R.id.written_user_answer_edit_text);
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
        Button submit = (Button) findViewById(R.id.question_submit_button);
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
        final Button submit = (Button) findViewById(R.id.question_submit_button);
        final Button gotoCamera = findViewById(R.id.goto_camera_button);
        picTakenImageView = findViewById(R.id.pic_taken_imageview);

        displayZone();
        displayPrompt();

        //Link gotoCamera button to camera
        gotoCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imageFile = cameraHandler.dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
            }
        });

        /* Display the choices */
        final ArrayList<String> choices = currentQuestion.getChoices();

        /* Create group for radio buttons */
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        /* Create choice radio button(s) */
        for(int i = 0; i < choices.size(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText(choices.get(i));

            radioGroup.addView(radioButton);

            /* Listen for a radio button to be selected. Once selected show the submit button*/
            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    submit.setVisibility(View.VISIBLE);
                }
            });

            /* Submits the response once user clicks. Response is the radio button selected */
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You selected: " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                    // Does not proceed unless they take a photo with camera
                    if (hasImage(picTakenImageView)) {
                        //Pass the answer to the controller
                        questionController.requestSubmitResponse(radioButton.getText().toString());
                        intentAway();
                    }
                    else {
                        Toast.makeText(v.getContext(), "Take a photo!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /* Add the radio button group to the view */
        LinearLayoutCompat layout = (LinearLayoutCompat) findViewById(R.id.pic_choices_llc);
        LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layout.addView(radioGroup, parameters);
    }


    /**
     * Used to intent to the next activity
     * Need to increase the Scavenger Hunt stage before we leave the activity to advance the game.
     * If this is the last question we need to intent to the Congrats Activity
     * If this is not the last question we need to intent to the
     */
    private void intentAway () {
        if (!gameController.requestCheckGameOver()) {
            gameController.requestIncrementCurrentStage();
            Intent intent = new Intent(QuestionActivity.this, LocationActivity.class);
            startActivity(intent);
            finish();

        } else{
            Intent intent = new Intent(QuestionActivity.this, CongratulationsActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * Checks if inputted ImageView contains an imageFile.
     * From http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android
     * accessed 10-24-2017
     * @param view
     * @return Bool, whether ImageView is empty
     */
    public boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }

    @Override
    /**
     * Display taken imageFile on imageView, and **FOR NOW** delete the fullsize photo
     * from storage.
     * From https://developer.android.com/training/camera/photobasics.html
     * accessed 10-24-2017
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            if (imageFile.exists()) {

                Bitmap downScaledBitMap = cameraHandler.downScaleBitMap(imageFile);
                picTakenImageView.setImageBitmap(downScaledBitMap);

                imageFile.delete();
            }
        }
    }

    /**
     * Choose which view to display
     * Gets the current question and all info to display the question on user interface
     * Determines type of question and how to display based on aspects of the question
     * Also handles user skipping a question
     *  1. Question is set to skipped
     *  2. Passes a blank response to question controller
     *  3. Moves to next activity using intent away Location Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        /* Attempt to get current question. If none, throw error */
        try {
            currentQuestion = questionController.requestQuestion();
        } catch (Exception NoQuestionError) {
            return;
        }

        //TODO Conditional for choosing the view that is less hacky
        if (currentQuestion.isChoicesEmpty())
            displayWrittenInput();
        else{
            displayPicInput();
            //displayMultChoice();
        }

        /* Skip button on all view */
        Button skipButton = (Button) findViewById(R.id.question_skip_button);

        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Question skipped", Toast.LENGTH_SHORT).show();
                questionController.skip(currentQuestion);
                questionController.requestSubmitResponse("");
                intentAway();

            }
        });


    }
}
