package com.cmput401f17.eplscavengerhunt.controller;

import android.graphics.Bitmap;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import javax.inject.Inject;

/**
 * QuestionController retrieves information about the current question
 * and handles the players response to the question
 */
public class QuestionController {

    private final ScavHuntState scavHuntState;

    @Inject
    public QuestionController(final ScavHuntState scavHuntState) {
        this.scavHuntState = scavHuntState;
    }

    /**
     * Record the player's response
     */
    public void requestSubmitResponse(final String responseString, final String answer) {
        Log.d("DEBUG", "Question answer: " + answer);
        Log.d("DEBUG", "Player's response: " + responseString);
        Response response = new Response(responseString);
        if (isPlayerCorrect(responseString, answer)) {
            response.markCorrect();
        }
        ;
        scavHuntState.addResponse(response);
    }

    /**
     * Checks the correctness of the player's response
     */
    private boolean isPlayerCorrect(final String response, final String answer) {
        return answer.toLowerCase().replaceAll("\\s+", "").
                equals(response.toLowerCase().replaceAll("\\s+", ""));
    }

    /**
     * Record the player's picture that they took
     * Note: the player is always correct for picture input type questions
     */
    public void requestSubmitResponseImage(final String answer, Bitmap imageFile) {
        Response response = new Response(answer);
        response.markCorrect();
        response.setImageFile(imageFile);
        scavHuntState.addResponse(response);
    }

    /**
     * Retrieve the current question
     */
    public Question requestQuestion() {
        return scavHuntState.getCurrentQuestion();
    }

    /**
     * Skip the current question
     */
    public void skip(Question question) {
        question.skip();
    }

    /**
     * Get the player's response to the current question
     */
    public Response requestResponse() {
        return scavHuntState.getCurrentResponse();
    }
}