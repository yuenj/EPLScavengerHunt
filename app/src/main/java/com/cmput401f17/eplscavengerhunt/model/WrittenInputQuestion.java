package com.cmput401f17.eplscavengerhunt.model;

/**
 * A model containing data for a question whose input is a typed answer
 */
public class WrittenInputQuestion extends Question {
    private String typedSolution;

    public WrittenInputQuestion() {
        this.typedSolution = "";
    }

    public String getTypedSolution() {
        return typedSolution;
    }

    public void setTypedSolution(String typedSolution) {
        this.typedSolution = typedSolution;
    }
}
