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
import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;

import javax.inject.Inject;

/**
 * Displays congratulations if they user inputted the correct answer, or
 * The correct answer and the users response if incorrect, and
 * displays the next zone to visit.
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

        // Get views
        final CardView cardCV = findViewById(R.id.CV_question_answer_card);
        final ImageView imageIV = findViewById(R.id.IV_question_answer_image);
        final TextView messageTV = findViewById(R.id.TV_question_answer_message);
        final TextView answerTV = findViewById(R.id.TV_question_answer_answer);
        final Button doneButton = findViewById(R.id.button_question_answer_done);

        // Determine if the answer is correct
        final Question question = questionController.requestQuestion();
        final Response response = questionController.requestResponse();

        boolean playerIsCorrect = false;
        // Compare the first character for multiple choice questions
        if (question instanceof MultipleChoiceQuestion) {
            if (question.getAnswer().charAt(0) == response.getResponseStr().charAt(0)) {
                playerIsCorrect = true;
            }
        }
        // Use Jen's method for written input questions
        else if (question instanceof WrittenInputQuestion) {
            playerIsCorrect = question.getAnswer().toLowerCase().replaceAll("\\s+", "").
                    equals(response.getResponseStr().toLowerCase().replaceAll("\\s+", ""));
        }
        // TODO: Define this later for pic input questions
        else {
            playerIsCorrect = true;
        }

        // Display feedback for the player
        if (playerIsCorrect) {
            final int resourceId = this.getResources().getIdentifier(
                    "ic_dolphin", "drawable", this.getPackageName());
            imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
            messageTV.setText(getResources().getText(R.string.correct_answer_text));
        } else {
            final int resourceId = this.getResources().getIdentifier(
                    "ic_monkey_wrong", "drawable", this.getPackageName());
            imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
            if (question.isSkipped()) {
                messageTV.setText(getResources().getText(R.string.skipped_answer_text));
            } else {
                messageTV.setText(getResources().getText(R.string.wrong_answer_text));
                messageTV.setTextSize(30);
            }
            cardCV.setCardBackgroundColor(Color.parseColor("#EF005D"));
        }

        // Gets the full answer instead of just 'A' or 'C'
        if (question instanceof MultipleChoiceQuestion) {
            for (String string: question.getChoices()) {
                if (string.charAt(0) == question.getAnswer().charAt(0)) {
                    answerTV.setText(string);
                }
            }
        } else {
            answerTV.setText(question.getAnswer());
        }

        final Intent intent;
        // If the game is not over give the player a location
        if (!gameController.requestCheckGameOver()) {
            doneButton.setText("VISIT NEXT LOCATION!");
            gameController.requestIncrementCurrentStage();
            intent = new Intent(QuestionAnswerActivity.this, LocationActivity.class);

        }
        // End game otherwise
        else {
            doneButton.setText("YOU'RE DONE!");
            intent = new Intent(QuestionAnswerActivity.this, CongratulationsActivity.class);
        }
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }
}
