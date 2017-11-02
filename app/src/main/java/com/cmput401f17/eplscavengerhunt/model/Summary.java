package com.cmput401f17.eplscavengerhunt.model;

import java.util.List;

/**
 * Holds all relevant information for displaying a summary of the game results
 */
public class Summary {

    private List<Response> responses;
    private List<Question> questions;
    private int score;
    private int numQuestions;

    public Summary(final List<Response> responses,
                   final List<Question> questions,
                   final int score,
                   final int numQuestions) {
        this.responses = responses;
        this.questions = questions;
        this.score = score;
        this.numQuestions = numQuestions;
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
