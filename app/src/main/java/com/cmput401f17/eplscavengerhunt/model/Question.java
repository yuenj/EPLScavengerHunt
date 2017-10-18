/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cmput401f17.eplscavengerhunt.model;

/**
 * Created by brettgarbitt on 2017-10-18.
 */

public class Question {

    private int questionID, zone;
    private String prompt, choices, solution, image_link, sound_link;
    private boolean take_pic, skipped;

    public Question() {
        this.questionID = 0;
        this.zone = 0;
        this.prompt = "";
        this.choices = "";
        this.solution = "";
        this.image_link = "";
        this.sound_link = "";
        this.take_pic = false;
        this.skipped = false;
    }

    public Question(int questionID, int zone, String questionText, String choices, String solution, String q_link, String s_link, boolean take_pic, boolean skipped) {
        this.questionID = questionID;
        this.zone = zone;
        this.prompt = questionText;
        this.choices = choices;
        this.solution = solution;
        this.image_link = q_link;
        this.sound_link = s_link;
        this.take_pic = false;
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

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) { this.choices = choices; }

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
