package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Question subclass that requires user to take a picture with device's camera.
 * For now identical to MultipleChoiceQuestion because we aren't required to
 * do anything with the picture, and the way the player chooses their response
 * is by selecting from a list of choices.
 * @see Question
 */
public class PicInputQuestion extends Question {
    List<String> choices;
    String picInputSolution;

    public PicInputQuestion() {
        super();
        this.choices = new ArrayList<>();
        this.picInputSolution = "";

    }

    public PicInputQuestion(List<String> choices, String picInputSolution) {
        super();
        this.choices = choices;
        this.picInputSolution = picInputSolution;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getPicInputSolution() {
        return picInputSolution;
    }

    public void setPicInputSolution(String picInputSolution) {
        this.picInputSolution = picInputSolution;
    }
}
