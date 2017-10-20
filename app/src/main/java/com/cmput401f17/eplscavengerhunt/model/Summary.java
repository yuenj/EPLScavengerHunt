package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all relevent information for displaying a summary of the game results
 */
public class Summary {

    private List<Response> responses;
    private List<Question> questions;
    private int score;
    private int numQuestions;

    public Summary() {
        this.responses = new ArrayList<>();
        this.score = 0;
        this.numQuestions = 0;
        this.questions = new ArrayList<>();
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
