package com.cmput401f17.eplscavengerhunt;

/**
 * Created by ryan on 2017-10-03.
 */

public class Question {

    private int questionID;
    private String questionText;
    private Boolean skipped;
    private String solution;

    public Question() {
        this.questionID = 0;
        this.questionText = "";
        this.skipped = false;
        this.solution = "";

    }

    public Question(int questionID, String questionText, String solution) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.solution = solution;
        this.skipped = false;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
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
