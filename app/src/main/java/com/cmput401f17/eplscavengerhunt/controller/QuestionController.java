package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;

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
    public QuestionController(ScavHuntState scavHuntState) {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        this.scavHuntState = scavHuntState;
    }

    /**
     *  Passes/ updates the user answer
     *  @pre User has input an answer
     *  @param answer
     */
    public void requestSubmitResponse(String answer){
        Response response = new Response(answer);
        scavHuntState.addResponse(response);
    }

    /**
     * Gets the data relating to the current question
     * @return A Question object relating to the current question
     */
    public Question requestQuestion() { return(scavHuntState.getCurrentQuestion()); }

    /**
     * User chose to skip question. Pass that along
     * @param question
     */
    public void skip(Question question){
        question.skip();
    }
}