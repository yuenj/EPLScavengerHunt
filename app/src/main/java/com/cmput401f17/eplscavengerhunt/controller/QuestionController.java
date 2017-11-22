package com.cmput401f17.eplscavengerhunt.controller;

import android.graphics.Bitmap;

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
     *  @pre User has input an answer
     *  @param answer       The user's answer
     */
    public void requestSubmitResponse(final String answer) {
        Response response = new Response(answer);
        scavHuntState.addResponse(response);
    }

    /**
     * Used to set the a users picture to the result
     * @param answer
     * @param imageFile
     */
    public void requestSubmitResponse(final String answer, Bitmap imageFile){
        if (requestQuestion() instanceof PicInputQuestion) {
            Response response = new Response(answer);
            response.setImageFile(imageFile);
            scavHuntState.addResponse(response);

        } else {
            Response response = new Response(answer);
            scavHuntState.addResponse(response);
        }
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