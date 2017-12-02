package com.cmput401f17.eplscavengerhunt.controller;

import android.graphics.Bitmap;
import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import javax.inject.Inject;

/**
 * An intermediate class that passes data
 */
public class QuestionController {

    private ScavHuntState scavHuntState;

    @Inject
    public QuestionController(final ScavHuntState scavHuntState) {
        this.scavHuntState = scavHuntState;
    }

    /**
     *  Passes/ updates the user answer
     *  Also confirms the correctness of the response
     *  @pre User has input an answer
     *  @param answer       The user's answer
     */
    public void requestSubmitResponse(final String responseString,final String answer) {
        Log.d("RequestMadeAnswer",answer.toLowerCase().replaceAll("\\s+", ""));
        Log.d("RequestMadeResponse",responseString.toLowerCase().replaceAll("\\s+", ""));
        Response response = new Response(responseString);
        if (answer.toLowerCase().replaceAll("\\s+", "").
                equals(responseString.toLowerCase().replaceAll("\\s+", ""))) {
            Log.d("Answer","Answer was corrcet");
            response.markCorrect();
        }

        Log.d("RequestSubmit","Request Was submitted");
        scavHuntState.addResponse(response);
    }

    /**
     * Used to set the a users picture to the result
     * Also confirms the correctness of the response
     * @param answer
     * @param imageFile
     */
    public void requestSubmitResponseImage(final String answer, Bitmap imageFile){
            Response response = new Response(answer);
            response.markCorrect();
            response.setImageFile(imageFile);
            scavHuntState.addResponse(response);
    }

    /**
     * Gets the data relating to the current question
     * @return A Question object relating to the current question
     */
    public Question requestQuestion() {
        return scavHuntState.getCurrentQuestion();
    }

    /**
     * User chose to skip question. Pass that along
     * @param question      The current question
     */
    public void skip(Question question){
        question.skip();
    }

    /** Gets the response to the current question */
    public Response requestResponse() {
        return scavHuntState.getCurrentResponse();
    }
}