package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;

/**
 * Model containing data for multiple questions
 */
public class MultChoiceQuestion extends Question {

    private ArrayList<String> choices;

    public MultChoiceQuestion () {
        this.choices = new ArrayList<String>();
    }

    public ArrayList<String> getChoices() { return(this.choices); }

    public void setChoices(ArrayList<String> choices) { this.choices = choices; }
}
