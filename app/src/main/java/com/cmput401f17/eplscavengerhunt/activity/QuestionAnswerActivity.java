package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        // find views
        final LinearLayout backgroundLL = findViewById(R.id.LL_question_answer_background);
        final CardView cardCV = findViewById(R.id.CV_question_answer_card);
        final ImageView imageIV = findViewById(R.id.IV_question_answer_image);
        final TextView messageTV = findViewById(R.id.TV_question_answer_message);
        final TextView answerTV = findViewById(R.id.TV_question_answer_answer);
        final CardView doneCV = findViewById(R.id.CV_question_answer_done_card);
        final Button doneButton = findViewById(R.id.button_question_answer_done);

        // determine player correctness
        final Question question = questionController.requestQuestion();
        final Response response = questionController.requestResponse();
        final boolean playerIsCorrect = question.getAnswer().toLowerCase().replaceAll("\\s+", "").
                equals(response.getResponseStr().toLowerCase().replaceAll("\\s+", ""));

        // Display feedback for the player
        if (playerIsCorrect) {
            final int resourceId = this.getResources().getIdentifier(
                    "correct", "drawable", this.getPackageName());
            imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
            messageTV.setText("Good Job!");
            backgroundLL.setBackgroundColor(Color.parseColor("#CCFF90"));
            cardCV.setCardBackgroundColor(Color.parseColor("#E040FB"));
            doneCV.setCardBackgroundColor(Color.parseColor("#F3E5F5"));
        } else {
            final int resourceId = this.getResources().getIdentifier(
                    "wrong", "drawable", this.getPackageName());
            imageIV.setImageDrawable(this.getResources().getDrawable(resourceId));
            if (question.isSkipped()) {
                messageTV.setText("You Skipped.");
            } else {
                messageTV.setText("Incorrect.");
            }
            backgroundLL.setBackgroundColor(Color.parseColor("#FF8A80"));
            cardCV.setCardBackgroundColor(Color.parseColor("#536DFE"));
            doneCV.setCardBackgroundColor(Color.parseColor("#E8EAF6"));
        }
        answerTV.setText(question.getAnswer());

        final Intent intent;
        // allow the player to visit the next location if the game is not over
        if (!gameController.requestCheckGameOver()) {

            doneButton.setText("VISIT NEXT LOCATION");
            gameController.requestIncrementCurrentStage();
            intent = new Intent(QuestionAnswerActivity.this, LocationActivity.class);
            // otherwise, display congratulations
        } else {
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
