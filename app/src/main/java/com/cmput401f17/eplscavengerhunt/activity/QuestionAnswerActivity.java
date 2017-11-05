package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.controller.SimpleCallback;
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

    private TextView resultTextView;
    private TextView zoneTextView;
    private Button viewSummaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        resultTextView = findViewById(R.id.TV_QuestionAnswer_result);
        zoneTextView = findViewById(R.id.TV_QuestionAnswer_zone);
        viewSummaryButton = findViewById(R.id.Button_QuestionAnswer_done);

        final Question question = questionController.requestQuestion();
        final Response response = questionController.requestResponse();
        final boolean playerIsCorrect = question.getAnswer().equals(response.getResponseStr());

        // TODO move hardcoded string values into string.xml
        // Display feedback for the player
        if (playerIsCorrect) {
            resultTextView.setText("Good job!");
        } else {
            resultTextView.setText("Wrong answer. The correct answer is " +  question.getAnswer());
        }

        // start polling players position if the game is not over
        if (!gameController.requestCheckGameOver()) {
            pollPosition();
            zoneTextView.setText("Go to Zone " + locationController.requestZone().getName());
            viewSummaryButton.setVisibility(View.GONE);
        } else { // otherwise, allow the player to go to the next screen
            zoneTextView.setText("You have completed the game!");
            viewSummaryButton.setVisibility(View.VISIBLE);
            viewSummaryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("QuestionAnswerActivity", "Going to CongratulationsActivity");
                    Intent intent = new Intent(QuestionAnswerActivity.this, CongratulationsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    void pollPosition() {
        // If the location is verified go to Question activity
        locationController.verifyLocation(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if (data) {
                    final Intent intent = new Intent(QuestionAnswerActivity.this, QuestionActivity.class);
                } else {
                    // This shouldn't go off
                    Log.d("LocationListener", "False Return");
                }
            }
        });
    }
}
