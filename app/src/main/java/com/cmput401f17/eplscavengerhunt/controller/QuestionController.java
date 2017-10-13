package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;

/**
 * An intermediate class that passes data
 */
public class QuestionController {

    private ScavHuntState huntState;

    public QuestionController() {
        huntState = new ScavHuntState();
    }

    /**
     * Gets the data relating to the current question
     * @return A Question object relating to the current question
     */
    public Question requestQuestion() {
        Question question = new Question(1, "Test", "TestAnswer");
        return(question);
        //return(huntState.getCurrentQuestion());
    }

    /**
     * Gets the current zone's name
     * @return A string representing the Zone
     */
    public String requestZone() {
        Zone zone = new Zone("Test");
        zone.setName("1");

        return(zone.getName());
        //return(huntState.getCurrentZone().getName());
    }

    /**
     *  Passes/ updates the user answer
     *  @pre User has input an answer
     *  @param answer
     */
    public void requestSubmitResponse(String answer){
        Response response = new Response(answer);
        System.out.println(response.getResponseStr());
        //huntState.addResponse(response);
    }

    public void skip(Question question){

    }

}