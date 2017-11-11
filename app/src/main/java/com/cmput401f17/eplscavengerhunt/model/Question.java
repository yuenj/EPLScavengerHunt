package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Question {

    private int questionID;
    private String zone, prompt, solution, answer, imageLink, soundLink, branch;
    private boolean takePic, skipped;
    private List<String> choices;

    public Question() {
        this.questionID = 0;
        this.prompt = "";
        this.imageLink = "";
        this.soundLink = "";
        this.skipped = false;
        this.zone = "";
        this.choices = null;
        this.solution = "";
        this.answer = "";
        this.takePic = false;
    }

    public Question(int questionID, String prompt, String answer, String imageLink) {
        this.questionID = questionID;
        this.prompt = prompt;
        this.answer = answer;
        this.solution = answer;
        this.imageLink = imageLink;
    }
    // Every question will have an image associated with it
    public Question(int questionID, String zone, String prompt, List<String> choices, String solution, String imageLink, String soundLink, boolean takePic, boolean skipped, String answer) {
        this.questionID = questionID;
        this.zone = zone;
        this.prompt = prompt;
        this.choices = choices;
        this.solution = solution;
        this.answer = answer;
        this.imageLink = imageLink;
        this.soundLink = soundLink;
        this.takePic = takePic;
        this.skipped = skipped;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isTakePic() {
        return takePic;
    }

    public void setTake_pic(boolean takePic) {
        this.takePic = takePic;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
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
