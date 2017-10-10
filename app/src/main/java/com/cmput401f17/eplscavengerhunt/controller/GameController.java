package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;

import java.util.ArrayList;

public class GameController {

    public GameController() {

    }

    public String initGame() {
        return "Dagger is working!";
    }

    public ArrayList<Question> retrieveQuestions() {
        Question question1 = new Question();
        question1.setQuestionText("question 1 prompt");
        question1.setSolution("question 1 solution");
        Question question2 = new Question();
        question2.setQuestionText("question 2 prompt");
        question2.setSolution("question 2 solution");

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        return questions;
    }

    public ArrayList<Response> retrieveResponses() {
        Response response1 = new Response();
        response1.setResponseStr("question 1 response");
        response1.markCorrect();
        Response response2 = new Response();
        response2.setResponseStr("question 2 response");
        response2.markIncorrect();

        ArrayList<Response> responses = new ArrayList<>();
        responses.add(response1);
        responses.add(response2);

        return responses;
    }

    public int retrieveTotalScore() {
        return 5;
    }
}
