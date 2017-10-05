package com.cmput401f17.eplscavengerhunt.model;

/**
 * Created by ryan on 2017-10-05.
 */

public class ScavHuntState {
    private String branch;
    private int[] questions;
    //private Zone[] zoneRoute;
    private int currentStage;
    private String[] playerResponses;
    private int numCorrect;
    private int numQuestions = 0;


    public ScavHuntState() {
        this.branch = "";
        this.questions = null;
        //this.zoneRoute = null;
        this.currentStage = 0;
        this.playerResponses = null;
        this.numCorrect = 0;

    }

    public ScavHuntState(String branch, int[] questions, /* Zone[] zoneRoute,*/ int currentStage, String[] playerResponses, int numCorrect) {
        this.branch = branch;
        this.questions = questions;
        //this.zoneRoute = zoneRoute;
        this.currentStage = currentStage;
        this.playerResponses = playerResponses;
        this.numCorrect = numCorrect;

    }

    public void generateResults() {

    }


    public void incrementNumCorrect() {
        this.numCorrect += 1;
    }

    private Boolean checkIfCorrect() {
        return true;

    }

    public void updateCurrentStage() {

    }


    public Boolean isGameOver() {
        return playerResponses.length == numQuestions;
    }


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int[] getQuestions() {
        return questions;
    }

    public void setQuestions(int[] questions) {
        this.questions = questions;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public String[] getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(String[] playerResponses) {
        this.playerResponses = playerResponses;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }
}
