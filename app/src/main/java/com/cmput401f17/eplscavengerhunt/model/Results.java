package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;
import java.util.List;

public class Results {
    private List<Response> responses;
    private int score;
    private int numQuestions;

    public Results() {
        this.responses = new ArrayList<>();
        this.score = 0;
        this.numQuestions = 0;
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
}
