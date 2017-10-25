package com.cmput401f17.eplscavengerhunt.model;


public class WrittenInputQuestion extends Question {
    String writtenInputSolution;

    public WrittenInputQuestion() {
        super();
        this.writtenInputSolution = "";
    }

    public WrittenInputQuestion(int questionID, String prompt, String imageLink,
                                String writtenInputSolution) {
        super(questionID, prompt, imageLink);
        this.writtenInputSolution = writtenInputSolution;
    }

    public String getWrittenInputSolution() {
        return writtenInputSolution;
    }

    public void setWrittenInputSolution(String writtenInputSolution) {
        this.writtenInputSolution = writtenInputSolution;
    }
}
