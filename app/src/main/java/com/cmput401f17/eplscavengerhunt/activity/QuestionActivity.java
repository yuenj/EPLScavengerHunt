package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.custom.CameraHandler;
import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * The Question page of the app
 * has three variants:
 *                      multiple choice question,
 *                      written answer question, and
 *                      picture input question
 */
public class QuestionActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private final CameraHandler cameraHandler = new CameraHandler(this);
    @Inject
    QuestionController questionController;
    @Inject
    GameController gameController;
    @Inject
    LocationController locationController;
    private Question currentQuestion;
    private ImageView picTakenIV;
    private CardView takeAPicCV;
    private File imageFile;
    private Bitmap downScaledBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        currentQuestion = questionController.requestQuestion();

        displayLayout();

        // Skip the question when the skip button is pressed
        final Button skipButton = findViewById(R.id.question_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                skipButton.setEnabled(false); // prevent skipping again
                questionController.skip(currentQuestion);
                questionController.requestSubmitResponse("",currentQuestion.getAnswer());
                skipButton.setEnabled(false);

                startQuestionAnswerActivity();
                finish();
            }
        });
    }

    /**
     * Displays the appropriate layout depending on type of question received
     */
    private void displayLayout() {
        if (currentQuestion instanceof WrittenInputQuestion) {
            displayWrittenAnswerLayout();
        } else if (currentQuestion instanceof MultipleChoiceQuestion) {
            displayMultipleChoiceLayout();
        } else if (currentQuestion instanceof PicInputQuestion) {
            displayPictureInputLayout();
        }
    }

    private void displayMultipleChoiceLayout() {
        setContentView(R.layout.activity_multiple_choice);

        // find views
        final CardView choiceOneCV = findViewById(R.id.CV_mc_choice_one);
        final CardView choiceTwoCV = findViewById(R.id.CV_mc_choice_two);
        final CardView choiceThreeCV = findViewById(R.id.CV_mc_choice_three);
        final CardView choiceFourCV = findViewById(R.id.CV_mc_choice_four);
        final RadioButton choiceOneRB = findViewById(R.id.RB_mc_choice_one);
        final RadioButton choiceTwoRB = findViewById(R.id.RB_mc_choice_two);
        final RadioButton choiceThreeRB = findViewById(R.id.RB_mc_choice_three);
        final RadioButton choiceFourRB = findViewById(R.id.RB_mc_choice_four);
        final ImageView pictureIV = findViewById(R.id.RIV_mc_picture);
        final Button confirmButton = findViewById(R.id.button_mc_confirm);

        // put the choice ui items in a list so they can be handled as a group
        final List<String> choices = currentQuestion.getChoices();
        final List<CardView> choiceCardViews = Arrays.asList(
                choiceOneCV, choiceTwoCV, choiceThreeCV, choiceFourCV);
        final List<RadioButton> choiceRadioButtons = Arrays.asList(
                choiceOneRB, choiceTwoRB, choiceThreeRB, choiceFourRB);

        // set views
        displayCategory();
        displayPrompt();
        displayChoices(choiceCardViews, choiceRadioButtons, choices);
        // display the image associated with the question
        Picasso.with(getApplicationContext()).load(currentQuestion.getImageLink()).fit().into(pictureIV);

        // set on click listeners
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String choice = null;
                // check if the player has selected a choice
                for (int i = 0; i < choices.size(); ++i) {
                    if (choiceRadioButtons.get(i).isChecked()) {
                        choice = choices.get(i);
                        break;
                    }
                }
                // if so, submit the response
                if (choice != null) {
                    confirmButton.setEnabled(false);
                    questionController.requestSubmitResponse(choice,currentQuestion.getAnswer());
                    startQuestionAnswerActivity();
                    finish();
                } else {
                    // otherwise, prompt the player to select a choice
                    Toast.makeText(view.getContext(), "Choose an answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayWrittenAnswerLayout() {
        setContentView(R.layout.activity_written_input);

        // find views
        final EditText editText = findViewById(R.id.written_user_answer_edit_text);
        final ImageView pictureIV = findViewById(R.id.question_picture);

        // set views
        displayCategory();
        displayPrompt();
        // display the image associated with the question
        Picasso.with(getApplicationContext()).load(currentQuestion.getImageLink()).fit().into(pictureIV);


        // set listener for enter/send button
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND &&
                        writtenAnswerChecker(view, editText)) {
                    startQuestionAnswerActivity();
                    handled = true;
                }
                return handled;
            }
        });

        // set listener for submit button
        final Button submit = findViewById(R.id.question_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                submit.setEnabled(false);
                if (writtenAnswerChecker(view, editText)) {
                    startQuestionAnswerActivity();
                    return;
                } else {
                    submit.setEnabled(true);
                }
            }
        });
    }


    /**
     * Displays view for a picture input question
     */

    private void displayPictureInputLayout() {
        setContentView(R.layout.activity_pic_input);

        // find views
        final CardView choiceOneCV = findViewById(R.id.CV_pic_choice_one);
        final CardView choiceTwoCV = findViewById(R.id.CV_pic_choice_two);
        final CardView choiceThreeCV = findViewById(R.id.CV_pic_choice_three);
        final CardView choiceFourCV = findViewById(R.id.CV_pic_choice_four);
        final RadioButton choiceOneRB = findViewById(R.id.RB_pic_choice_one);
        final RadioButton choiceTwoRB = findViewById(R.id.RB_pic_choice_two);
        final RadioButton choiceThreeRB = findViewById(R.id.RB_pic_choice_three);
        final RadioButton choiceFourRB = findViewById(R.id.RB_pic_choice_four);
        final Button confirmButton = findViewById(R.id.button_pic_confirm);
        picTakenIV = findViewById(R.id.RIV_pic_picture);
        takeAPicCV = findViewById(R.id.CV_pic_take_a_picture);

        // put the choice ui items in a list so they can be handled as a group
        final List<String> choices = currentQuestion.getChoices();
        final List<CardView> choiceCardViews = Arrays.asList(choiceOneCV, choiceTwoCV,
                choiceThreeCV, choiceFourCV);
        final List<RadioButton> choiceRadioButtons = Arrays.asList(choiceOneRB, choiceTwoRB,
                choiceThreeRB, choiceFourRB);

        // set views
        displayCategory();
        displayPrompt();
        displayChoices(choiceCardViews, choiceRadioButtons, choices);

        // set listener for camera button
        takeAPicCV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imageFile = cameraHandler.dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
            }
        });

        // set listener for confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String choice = null;
                // verify player has selected a choice
                for (int i = 0; i < choices.size(); ++i) {
                    if (choiceRadioButtons.get(i).isChecked()) {
                        choice = choices.get(i);
                        break;
                    }
                }
                // if the player has made a choice and taken a picture, submit the response
                if (choice != null && hasImage(picTakenIV)) {
                    questionController.requestSubmitResponseImage(choice, downScaledBitMap);
                    confirmButton.setEnabled(false);
                    startQuestionAnswerActivity();
                    finish();
                // prompt the user to take a picture if they haven't taken one
                } else if (!hasImage(picTakenIV)) {
                    Toast.makeText(view.getContext(), "Take a photo!", Toast.LENGTH_SHORT).show();
                // prompt the user to select a choice if they haven't chosen one
                } else {
                    Toast.makeText(view.getContext(), "Choose an answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayCategory() {
        final TextView zoneView = findViewById(R.id.question_zone_text_view);
        final CardView card = findViewById(R.id.card_view);

        zoneView.setText(locationController.requestZone().getCategory() + " : " + locationController.requestZone().getName());
        // Change the background colour to match that of the current zone
        card.setCardBackgroundColor(Color.parseColor(locationController.requestZone().getColor()));
    }

    private void displayPrompt() {
        TextView prompt = findViewById(R.id.question_prompt_text_view);
        prompt.setText(currentQuestion.getPrompt());
    }

    private void displayChoices(List<CardView> choiceCardViews, final List<RadioButton> choiceRadioButtons,
                                final List<String> choices) {

        // multiple choice options are hidden by default
        for (int i = 0; i < choices.size(); ++i) {
            final RadioButton radioButton = choiceRadioButtons.get(i);
            final CardView cardView = choiceCardViews.get(i);
            radioButton.setText(choices.get(i));
            radioButton.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);

            final int id = i;
            // un-select other options when a choice has been selected
            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    radioButton.setChecked(true);
                    for (int j = 0; j < choices.size(); ++j) {
                        if (j != id) {
                            choiceRadioButtons.get(j).setChecked(false);
                        }
                    }
                }
            });
        }
    }

    /**
     * Ensure that an answer is entered (>0 characters)
     * Taken from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     */
    private boolean writtenAnswerChecker(View view, EditText editText) {
        if (editText.getText().length() == 0) {
            Toast.makeText(view.getContext(), "Answer too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Hides the on-screen (soft) keyboard
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        questionController.requestSubmitResponse(editText.getText().toString(),currentQuestion.getAnswer());
        return true;
    }

    /**
     * Check if the player has taken an image
     * Taken from http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android
     * Accessed 10-24-2017
     */
    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    private void startQuestionAnswerActivity() {
        Intent intent = new Intent(QuestionActivity.this, QuestionAnswerActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Display the taken picture, and **FOR NOW** delete the fullsize photo
     * from storage.
     * Taken from https://developer.android.com/training/camera/photobasics.html
     * Accessed 10-24-2017
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (imageFile.exists()) {
                downScaledBitMap = cameraHandler.downScaleBitMap(imageFile);
                picTakenIV.setImageBitmap(downScaledBitMap);
                takeAPicCV.setVisibility(View.GONE);
            }
        }
    }
}
