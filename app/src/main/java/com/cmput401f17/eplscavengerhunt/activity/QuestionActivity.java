package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.custom.CameraHandler;
import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class QuestionActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final CameraHandler cameraHandler = new CameraHandler(this);
    @Inject
    QuestionController questionController;
    @Inject
    LocationController locationController;
    private Question currentQuestion;
    private ImageView picTakenIV;
    private CardView takeAPicCV;
    private File imageFile;

    /**
     * Displays the current zone
     */
    private void displayZone() {
        TextView zoneView = findViewById(R.id.question_zone_text_view);
        zoneView.setText("Zone: " + locationController.requestZone().getName());
    }

    /**
     * Displays the current question prompt
     */
    private void displayPrompt() {
        TextView prompt = findViewById(R.id.question_prompt_text_view);
        prompt.setText("Task: " + currentQuestion.getPrompt());
    }

    /**
     * Displays the view for a multiple choice question
     * 1. Display Zone, and Prompt
     * 2. Add buttons according to the number of choices in the question
     * 3. Listen for user to press button. When pressed pass on answer to controller.
     * 4. Use startQuestionAnswerActivity to move to next activity
     */
    private void displayMultChoice() {
        setContentView(R.layout.activity_multiple_choice);

        // find views
        final TextView zoneTV = findViewById(R.id.TV_mc_zone);
        final TextView areaTV = findViewById(R.id.TV_mc_area);
        final ImageView pictureIV = findViewById(R.id.RIV_mc_picture);
        final TextView promptTV = findViewById(R.id.TV_mc_prompt);
        final RadioButton choiceOneRB = findViewById(R.id.RB_mc_choice_one);
        final RadioButton choiceTwoRB = findViewById(R.id.RB_mc_choice_two);
        final RadioButton choiceThreeRB = findViewById(R.id.RB_mc_choice_three);
        final RadioButton choiceFourRB = findViewById(R.id.RB_mc_choice_four);
        final Button confirmButton = findViewById(R.id.button_mc_confirm);

        // get the current zone and question
        final Zone zone = locationController.requestZone();
        final MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionController.requestQuestion();
        final List<String> choices = question.getChoices();
        final List<RadioButton> choiceRadioButtons = Arrays.asList(choiceOneRB, choiceTwoRB,
                choiceThreeRB, choiceFourRB);

        // set up the view displays
        zoneTV.setText("Zone " + zone.getName());
        areaTV.setText(zone.getArea()); // TODO capitalize every word with a util function
        final int resourceId = this.getResources()
                .getIdentifier(question.getImageLink(), "drawable", this.getPackageName());
        final Drawable drawable = this.getResources().getDrawable(resourceId);
        pictureIV.setImageDrawable(drawable);
        promptTV.setText(question.getPrompt());

        // TODO in multiplechoicequestion model - create a guard against setting more than four choices when we retrieve from DB
        // and validate theres at least min num of choices
        // or simply discard the rest
        // display choices on radio buttons
        int i;
        for (i = 0; i < choices.size(); ++i) {
            final RadioButton radioButton = choiceRadioButtons.get(i);
            radioButton.setText(choices.get(i));
            final int id = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    radioButton.setChecked(true);
                    // un-check other choices
                    for (int j = 0; j < choices.size(); ++j) {
                        if (j != id) {
                            choiceRadioButtons.get(j).setChecked(false);
                        }
                    }
                }
            });
        }
        // hide the extra radio buttons if there are less choices than buttons
        while (i < choiceRadioButtons.size()) {
            choiceRadioButtons.get(i++).setVisibility(View.GONE);
        }

        // Submits the response once user clicks. Response is the radio button selected
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // get the selected choice
                String choice = null;
                for (int i = 0; i < choices.size(); ++i) {
                    if (choiceRadioButtons.get(i).isChecked()) {
                        choice = choices.get(i);
                        break;
                    }
                }
                // display correctness of the response in the next screen
                if (choice != null) {
                    questionController.requestSubmitResponse(choice);
                    startQuestionAnswerActivity();
                } else {
                    Toast.makeText(view.getContext(), "Choose a answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Ensure that an answer is entered (>0 characters)
     * <p>
     * If answer is entered:
     * 1.Makes a toast to inform user answer has been received.
     * 2.Gets the user input nad changes to string.
     * 3.Hide the on-screen/ soft keyboard
     * 4.Pass the answer to the controller for further use.
     * 5.Use startQuestionAnswerActivity to move to next activity
     * <p>
     * Modified code originally from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     *
     * @param view, editText
     */
    private void writtenAnswerChecker(View view, EditText editText) {
        TextInputLayout userAnswerLayout = findViewById(R.id.written_user_answer_wrapper_til);

        if (editText.getText().length() == 0) {
            userAnswerLayout.setError("Answer is too short.");
        } else {
            userAnswerLayout.setErrorEnabled(false);

            Toast.makeText(view.getContext(), "Answer Submitted!", Toast.LENGTH_SHORT).show();

            // Hides the on-screen (soft) keyboard 
            if (view != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            questionController.requestSubmitResponse(editText.getText().toString());
            startQuestionAnswerActivity();
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

        // Modified code whose original is from https://developer.android.com/training/keyboard-input/style.html 
        // User's keyboard has a send button, which when pressed will submit the answer the user typed in 
        final EditText editText = findViewById(R.id.written_user_answer_edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                // Answer sent through keyboard send button 
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    writtenAnswerChecker(v, editText);
                    handled = true;
                }

                return handled;
            }
        });

        // Listens for when button is pressed. When it is pressed, answer is submitted 
        Button submit = (Button) findViewById(R.id.question_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                writtenAnswerChecker(view, editText);
            }
        });
    }

    /**
     * Displays view for a picture input question
     */
    private void displayPicInput() {
        setContentView(R.layout.activity_pic_input);

        // find views
        picTakenIV = findViewById(R.id.RIV_pic_picture);
        final TextView zoneTV = findViewById(R.id.TV_pic_zone);
        final TextView areaTV = findViewById(R.id.TV_pic_area);
        takeAPicCV = findViewById(R.id.CV_pic_take_a_picture);
        final TextView promptTV = findViewById(R.id.TV_pic_prompt);
        final RadioButton choiceOneRB = findViewById(R.id.RB_pic_choice_one);
        final RadioButton choiceTwoRB = findViewById(R.id.RB_pic_choice_two);
        final RadioButton choiceThreeRB = findViewById(R.id.RB_pic_choice_three);
        final RadioButton choiceFourRB = findViewById(R.id.RB_pic_choice_four);
        final Button confirmButton = findViewById(R.id.button_pic_confirm);

        // get the current zone and question
        final Zone zone = locationController.requestZone();
        final PicInputQuestion question = (PicInputQuestion) questionController.requestQuestion();
        final List<String> choices = question.getChoices();
        final List<RadioButton> choiceRadioButtons = Arrays.asList(choiceOneRB, choiceTwoRB,
                choiceThreeRB, choiceFourRB);

        //Link takeAPic view to camera
        takeAPicCV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imageFile = cameraHandler.dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
            }
        });

        // set up the view displays
        zoneTV.setText("Zone " + zone.getName());
        areaTV.setText(zone.getArea());
        promptTV.setText(question.getPrompt());

        // display choices on radio buttons
        int i;
        for (i = 0; i < choices.size(); ++i) {
            final RadioButton radioButton = choiceRadioButtons.get(i);
            radioButton.setText(choices.get(i));
            final int id = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    radioButton.setChecked(true);
                    // un-check other choices
                    for (int j = 0; j < choices.size(); ++j) {
                        if (j != id) {
                            choiceRadioButtons.get(j).setChecked(false);
                        }
                    }
                }
            });
        }
        // hide the extra radio buttons if there are less choices than buttons
        while (i < choiceRadioButtons.size()) {
            choiceRadioButtons.get(i++).setVisibility(View.GONE);
        }

        // Submits the response once user clicks. Response is the radio button selected
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // get the selected choice
                String choice = null;
                for (int i = 0; i < choices.size(); ++i) {
                    if (choiceRadioButtons.get(i).isChecked()) {
                        choice = choices.get(i);
                        break;
                    }
                }
                // display correctness of the response in the next screen
                if (choice != null && hasImage(picTakenIV)) {
                    questionController.requestSubmitResponse(choice);
                    startQuestionAnswerActivity();
                } else if (!hasImage(picTakenIV)) {
                    Toast.makeText(view.getContext(), "Take a photo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Choose a answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Starts the Question Answer Activity to display immediate results to the user.
     */
    private void startQuestionAnswerActivity() {
        Intent intent = new Intent(QuestionActivity.this, QuestionAnswerActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Checks if inputted ImageView contains an imageFile.
     * From http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android
     * accessed 10-24-2017
     *
     * @param view
     * @return Bool, whether ImageView is empty
     */
    public boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
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
                picTakenIV.setImageBitmap(downScaledBitMap);
                takeAPicCV.setVisibility(View.GONE);

                imageFile.delete();
            }
        }
    }

    /**
     * Choose which view to display
     * Gets the current question and all info to display the question on user interface
     * Determines type of question and how to display based on aspects of the question
     * Also handles user skipping a question
     * 1. Question is set to skipped
     * 2. Passes a blank response to question controller
     * 3. Moves to next activity using intent away Location Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        try {
            currentQuestion = questionController.requestQuestion();
        } catch (Exception NoQuestionError) {
            return;
        }

        displayLayout();

        // Skip the question when the skip button is pressed
        Button skipButton = findViewById(R.id.question_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Question skipped", Toast.LENGTH_SHORT).show();
                questionController.skip(currentQuestion);
                questionController.requestSubmitResponse("");
                startQuestionAnswerActivity();
            }
        });
    }

    /**
     * Displays the appropriate layout depending on type of question received.
     */
    private void displayLayout() {
        if (currentQuestion instanceof WrittenInputQuestion)
            displayWrittenInput();
        else if (currentQuestion instanceof MultipleChoiceQuestion)
            displayMultChoice();
        else if (currentQuestion instanceof PicInputQuestion)
            displayPicInput();
    }
}
