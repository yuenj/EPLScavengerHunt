package com.cmput401f17.eplscavengerhunt.model;

/**
 * Holds information for the user response
 */
public class Response {

    private String responseStr;
    private Boolean correct;

    public Response(final String responseStr) {
        this.responseStr = responseStr;
        this.correct = true;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void markCorrect() {
        this.correct = true;
    }

    public void markIncorrect() {
        this.correct = false;
    }
}
