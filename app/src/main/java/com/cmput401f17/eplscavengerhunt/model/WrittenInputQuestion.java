package com.cmput401f17.eplscavengerhunt.model;


public class WrittenInputQuestion {
    String writtenInputSolution;

    public WrittenInputQuestion() {
        super();
        this.writtenInputSolution = "";
    }

    public WrittenInputQuestion(String writtenInputSolution) {
        super();
        this.writtenInputSolution = writtenInputSolution;
    }

    public String getWrittenInputSolution() {
        return writtenInputSolution;
    }

    public void setWrittenInputSolution(String writtenInputSolution) {
        this.writtenInputSolution = writtenInputSolution;
    }
}
