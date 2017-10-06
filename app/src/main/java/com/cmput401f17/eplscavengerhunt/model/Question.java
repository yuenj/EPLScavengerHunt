package com.cmput401f17.eplscavengerhunt.model;


public class Question {

    private int questionID;
    private String prompt;
    private Boolean skipped;
    private String solution;

    public Question() {
        this.questionID = 0;
        this.prompt = "";
        this.skipped = false;
        this.solution = "";
    }

    public Question(int questionID, String questionText, String solution) {
        this.questionID = questionID;
        this.prompt = questionText;
        this.solution = solution;
        this.skipped = false;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionPrompt() {
        return prompt;
    }

    public void setQuestionText(String questionText) {
        this.prompt = questionText;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Boolean isSkipped() {
        return skipped;
    }

    public void skip() {
        this.skipped = true;
    }

}
