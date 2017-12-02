package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * The Question Answer page of the app
 * Displays feedback and the correct answer to the player
 */
public class QuestionAnswerActivity extends AppCompatActivity {

    @Inject
    GameController gameController;
    @Inject
    LocationController locationController;
    @Inject
    QuestionController questionController;
    @Inject
    ScavHuntState scavHuntState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        // an image will be randomly picked from this container
        final List<String> correctImages = Arrays.asList("ic_bat", "ic_bee", "ic_butterfly",
                "ic_chick", "ic_dog_body", "ic_dolphin", "ic_duck",
                "ic_eagle", "ic_fox", "ic_goat", "ic_hamster", "ic_koala", "ic_ladybug",
                "ic_leopard", "ic_lizard", "ic_monkey_body", "ic_monkey_correct",
                "ic_monkey_cover_ear", "ic_octopus", "ic_owl", "ic_panda", "ic_penguin",
                "ic_shark", "ic_spider", "ic_turtle");
        final List<String> skippedImages = Arrays.asList("ic_blue_bird", "ic_fish",
                "ic_rooster", "ic_frog");
        final List<String> incorrectImages = Arrays.asList("ic_gorilla", "ic_monkey_wrong",
                "ic_shrimp", "ic_squirrel");
        final Random random = new Random();

        // find views
        final CardView cardCV = findViewById(R.id.CV_question_answer_card);
        final ImageView imageIV = findViewById(R.id.IV_question_answer_image);
        final TextView messageTV = findViewById(R.id.TV_question_answer_message);
        final TextView answerTV = findViewById(R.id.TV_question_answer_answer);
        final Button doneButton = findViewById(R.id.button_question_answer_done);

        final Question question = questionController.requestQuestion();
        final Response response = questionController.requestResponse();

        // the layout of the user interface depends on the players correctness
        if (question instanceof PicInputQuestion && !question.isSkipped()) {
            // display the players picture that they took with the camera
            imageIV.setImageBitmap(response.getImageFile());
        } else {
            int index;
            int resourceId;
            if (response.isCorrect()) {
                // the player answered the question correctly
                index = random.nextInt(correctImages.size());
                resourceId = this.getResources().getIdentifier(
                        correctImages.get(index), "drawable", this.getPackageName());
                imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
                messageTV.setText(getResources().getText(R.string.correct_answer_text));
            } else {
                if (question.isSkipped()) {
                    // the player skipped the question
                    index = random.nextInt(skippedImages.size());
                    resourceId = this.getResources().getIdentifier(
                            skippedImages.get(index), "drawable", this.getPackageName());
                    imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
                    messageTV.setText(getResources().getText(R.string.skipped_answer_text));
                    cardCV.setCardBackgroundColor(Color.parseColor("#EF005D")); // red
                } else {
                    // the player answered the question incorrectly
                    index = random.nextInt(incorrectImages.size());
                    resourceId = this.getResources().getIdentifier(
                            incorrectImages.get(index), "drawable", this.getPackageName());
                    imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
                    messageTV.setText(getResources().getText(R.string.wrong_answer_text));
                    cardCV.setCardBackgroundColor(Color.parseColor("#EF005D")); // red
                }
            }
        }

        // display the correct answer
        answerTV.setText(question.getAnswer());

        final Intent intent;
        // if the game is not over, give the player a location
        if (!gameController.requestCheckGameOver()) {
            doneButton.setText("VISIT NEXT LOCATION!");
            gameController.requestIncrementCurrentStage();
            intent = new Intent(QuestionAnswerActivity.this, LocationActivity.class);
        }
        // end game otherwise
        else {
            doneButton.setText("HURRAY!");
            intent = new Intent(QuestionAnswerActivity.this, CongratulationsActivity.class);
        }

        // set on click listeners
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doneButton.setEnabled(false);
                startActivity(intent);
                finish();
            }
        });
    }
}
