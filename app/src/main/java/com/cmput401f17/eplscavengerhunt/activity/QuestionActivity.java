package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.squareup.picasso.Picasso;
import java.io.File;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class QuestionActivity extends AppCompatActivity {

    @Inject
    QuestionController questionController;

    @Inject
    LocationController locationController;

    private Question currentQuestion;

    // Attributes related to user picture input
    private ImageView picTakenImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File imageFile;
    final CameraHandler cameraHandler = new CameraHandler(this);

    /**
     * Displays the name of the current zone
     * Change the background colour to match that of the current zone
     */
    private void displayZone() {
        TextView zoneView = (TextView) findViewById(R.id.question_zone_text_view);
        zoneView.setText("Location: " + locationController.requestZone().getName());
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(locationController.requestZone().getColor()));
    }

    /** Displays the current question prompt */
    private void displayPrompt() {
        TextView prompt = (TextView)findViewById(R.id.question_prompt_text_view);
        prompt.setText("Question: " + currentQuestion.getPrompt());
    }

    /**
     * Uses Picasso to display the relate image for a question.
     */
    private void displayImage(){
        ImageView image = (ImageView)findViewById(R.id.question_image);
        Log.d("Image debug",currentQuestion.getImageLink());
        Picasso.with(getApplicationContext()).load(currentQuestion.getImageLink()).into(image);

    }


    /**
     * Displays the view for a multiple choice question
     *  1. Display Zone, and Prompt
     *  2. Add buttons according to the number of choices in the question
     *  3. Listen for user to press button. When pressed pass on answer to controller.
     *  4. Use startQuestionAnswerActivity to move to next activity
     */
    private void displayMultChoice(){
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
            choiceRadioButtons.get(i).setVisibility(View.GONE);
        }


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
                }
            }
        });
    }

    /**
     *  Ensure that an answer is entered (>0 characters)
     *
     *  If answer is entered:
     *      1.Makes a toast to inform user answer has been received.
     *      2.Gets the user input nad changes to string.
     *      3.Hide the on-screen/ soft keyboard
     *      4.Pass the answer to the controller for further use.
     *      5.Use startQuestionAnswerActivity to move to next activity
     *
     *  Modified code originally from https://code.tutsplus.com/tutorials/creating-a-login-screen-using-textinputlayout--cms-24168
     *
     *  @param view, editText
     */
    private void writtenAnswerChecker(View view, EditText editText) {
        TextInputLayout userAnswerLayout = findViewById(R.id.written_user_answer_wrapper_til);

        if(editText.getText().length() == 0){
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
     *  1. Display zone and prompt text
     *  2. Listen for user input, through the on-screen keyboard send, or by pressing the button
     */
    private void displayWrittenInput(){
        setContentView(R.layout.activity_written_input);

        displayZone();
        displayPrompt();
        displayImage();

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
     *  1. Display zone and prompt text
     */
    private void displayPicInput(){
        setContentView(R.layout.activity_pic_input);
        final Button submit = (Button) findViewById(R.id.question_submit_button);
        final Button gotoCamera = findViewById(R.id.goto_camera_button);
        picTakenImageView = findViewById(R.id.pic_taken_imageview);

        displayZone();
        displayPrompt();
        displayImage();

        //Link gotoCamera button to camera
        gotoCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                imageFile = cameraHandler.dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
            }
        });

        // Display the choices 
        final List<String> choices = ((PicInputQuestion) currentQuestion).getChoices();

        // Create group for radio buttons 
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        // Create choice radio button(s) 
        for(int i = 0; i < choices.size(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText(choices.get(i));

            radioGroup.addView(radioButton);

            // Listen for a radio button to be selected. Once selected show the submit button
            radioButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    submit.setVisibility(View.VISIBLE);
                }
            });

            // Submits the response once user clicks. Response is the radio button selected 
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You selected: " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                    // Does not proceed unless they take a photo with camera
                    if (hasImage(picTakenImageView)) {
                        //Pass the answer to the controller
                        questionController.requestSubmitResponse(radioButton.getText().toString());
                        startQuestionAnswerActivity();
                    }
                    else {
                        Toast.makeText(v.getContext(), "Take a photo!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Add the radio button group to the view 
        LinearLayoutCompat layout = findViewById(R.id.pic_choices_llc);
        LinearLayoutCompat.LayoutParams parameters = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layout.addView(radioGroup, parameters);
    }

    /** Starts the Question Answer Activity to display immediate results to the user. */
    private void startQuestionAnswerActivity () {
        Intent intent = new Intent(QuestionActivity.this, QuestionAnswerActivity.class);
        startActivity(intent);
        finish();
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

    /** Displays the appropriate layout depending on type of question received. */
    private void displayLayout() {
        if (currentQuestion instanceof WrittenInputQuestion) 
            displayWrittenInput();
        else if (currentQuestion instanceof MultipleChoiceQuestion) 
            displayMultChoice();
        else if (currentQuestion instanceof PicInputQuestion) 
            displayPicInput();
    }
}
