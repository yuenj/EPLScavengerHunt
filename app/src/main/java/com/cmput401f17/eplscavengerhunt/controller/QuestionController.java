package com.cmput401f17.eplscavengerhunt.controller;


import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import javax.inject.Inject;


/**
 * QuestionController handles obtaining a question,
 * passing the users answer to be stored and skipping a question
 */
public class QuestionController {

    private final ScavHuntState scavHuntState;

    @Inject
    public QuestionController(ScavHuntState scavHuntState) {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        this.scavHuntState = scavHuntState;
    }

    /**
     * Gets the data relating to the current question
     *
     * @return Question     A question object containing a prompt and a solution and
     * other specifics depending on the type of question
     */
    public Question requestQuestion() {

        return (scavHuntState.getCurrentQuestion());
    }


    /**
     * Updates the user's answer for the current question
     *
     * @param answer        The users answer to the current question
     */
    public void requestSubmitResponse(String answer) {
        Response response = new Response(answer);
        scavHuntState.addResponse(response);
    }

    /**
     * Skips the current question
     *
     * @param question      The question currently displayed to the user
     */
    public void skip(Question question) {
        question.skip();
    }
}