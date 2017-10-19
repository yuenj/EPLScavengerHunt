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

    private int questionID, zone;
    private String prompt, image_link, solution,sound_link;
    private ArrayList<String> choices;
    private boolean take_pic, skipped;

    public Question() {
        this.questionID = 0;
        this.zone = 0;
        this.prompt = "";
        this.choices = new ArrayList<String>();
        this.solution = "";
        this.image_link = "";
        this.sound_link = "";
        this.take_pic = false;
        this.skipped = false;
    }

    public Question(int questionID, String questionText, String solution) {
        this.questionID = questionID;
        this.zone = zone;
        this.prompt = questionText;
        this.choices = new ArrayList<String>();
        this.solution = solution;
        this.image_link = "";
        this.sound_link = "";
        this.take_pic = false;
        this.skipped = false;
    }

    public Question(int questionID, int zone, String questionText, String solution, String q_link, String s_link, boolean take_pic) {
        this.questionID = questionID;
        this.zone = zone;
        this.prompt = questionText;
        this.choices = new ArrayList<String>();
        this.solution = solution;
        this.image_link = q_link;
        this.sound_link = s_link;
        this.take_pic = take_pic;
        this.skipped = false;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
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

    public String getQ_link() { return image_link; }

    public void setQ_link(String q_link) { this.image_link = q_link; }

    public String getS_link() { return sound_link; }

    public void setS_link(String s_link) { this.sound_link = s_link; }

    public boolean isTake_pic() { return take_pic; }

    public void setTake_pic(boolean take_pic) { this.take_pic = take_pic; }

    public Boolean isSkipped() {
        return skipped;
    }

    public void skip() {
        this.skipped = true;
    }

}
