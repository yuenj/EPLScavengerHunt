package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;

class QuestionController {
    private static final QuestionController ourInstance = new QuestionController();

    static QuestionController getInstance() {
        return ourInstance;
    }

    private QuestionController() {}

    public void requestQuestion() {

        //TODO Determine view
        //getCurrentStage();
        //getQuestion(state);


    }

    public void requestSubmitResponse(){

    }

    public void skip(Question question){

    }

}