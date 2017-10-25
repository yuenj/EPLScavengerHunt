package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;

public abstract class Question {

    private int questionID;
    private String prompt;
    private String imageLink;
    private String soundLink;
    private boolean skipped;

    public Question() {
        this.questionID = 0;
        this.prompt = "";
        this.imageLink = "";
        this.soundLink = "";
        this.skipped = false;
    }

    public Question(int questionID, String prompt, String imageLink, String soundLink, boolean skipped) {
        this.questionID = questionID;
        this.prompt = prompt;
        this.imageLink = imageLink;
        this.soundLink = soundLink;
        this.skipped = skipped;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSoundLink() {
        return soundLink;
    }

    public void setSoundLink(String soundLink) {
        this.soundLink = soundLink;
    }

    public Boolean isSkipped() {
        return skipped;
    }

    public void skip() {
        this.skipped = true;
    }

}
