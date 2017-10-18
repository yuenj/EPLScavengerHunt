package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;

/**
 * Model containing data for questions where the user answers by taking a photo/ picture
 */
public class PicInputQuestion extends Question {

    private ArrayList<String> picIdentifier;
    private int picInputSolution;

    public PicInputQuestion() {
        this.picIdentifier = new ArrayList<String>();
        this.picInputSolution = 0;
    }

    public ArrayList<String> getPicIdentifier() {
        return picIdentifier;
    }

    public void setPicIdentifier(ArrayList<String> picIdentifier) {
        this.picIdentifier = picIdentifier;
    }

    public int getPicInputSolution() {
        return picInputSolution;
    }

    public void setPicInputSolution(int picInputSolution) {
        this.picInputSolution = picInputSolution;
    }
}
