/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;


public class Question {

    private int questionID;
    private String prompt, imageLink, solution,soundLink;
    private ArrayList<String> choices;
    private boolean takePic, skipped;

    public Question() {
        this.questionID = 0;
        this.prompt = "";
        this.choices = new ArrayList<>();
        this.solution = "";
        this.imageLink = "";
        this.soundLink = "";
        this.takePic = false;
        this.skipped = false;
    }

    public Question(int questionID, String questionText, String solution) {
        this.questionID = questionID;
        this.prompt = questionText;
        this.choices = new ArrayList<>();
        this.solution = solution;
        this.imageLink = "";
        this.soundLink = "";
        this.takePic = false;
        this.skipped = false;
    }

    public Question(int questionID, String questionText, String solution, String iLink, String sLink, boolean takePic) {
        this.questionID = questionID;
        this.prompt = questionText;
        this.choices = new ArrayList<>();
        this.solution = solution;
        this.imageLink = iLink;
        this.soundLink = sLink;
        this.takePic = takePic;
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

    public ArrayList<String> getChoices() { return(this.choices); }

    public void setChoices(ArrayList<String> choices) { this.choices = choices;}

    public boolean isChoicesEmpty(){ return(this.choices.isEmpty());}

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getImageLink () { return imageLink; }

    public void setImageLink(String iLink) { this.imageLink = iLink; }

    public String getSoundLink() { return soundLink; }

    public void setSoundLink(String sLink) { this.soundLink = sLink; }

    public boolean isTakePic() { return takePic; }

    public void setTakePic(boolean takePic) { this.takePic = takePic; }

    public Boolean isSkipped() {
        return skipped;
    }

    public void skip() {
        this.skipped = true;
    }

}
