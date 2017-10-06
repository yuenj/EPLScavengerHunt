package com.cmput401f17.eplscavengerhunt.model;


import java.util.ArrayList;

public class ScavHuntState {
    private String branch;
    private ArrayList<Question> questions;
    private ArrayList<Zone> zoneRoute;
    private int currentStage;
    private ArrayList<Response> playerResponses;
    private int numCorrect;
    private int numQuestions = 0;


    public ScavHuntState() {
        this.branch = "";
        this.questions = new ArrayList<>();
        this.zoneRoute = new ArrayList<>();
        this.currentStage = 0;
        this.playerResponses = new ArrayList<>();
        this.numCorrect = 0;

    }

    public ScavHuntState(String branch, ArrayList<Question> questions, ArrayList<Zone> zoneRoute, int currentStage, ArrayList<Response> playerResponses, int numCorrect) {
        this.branch = branch;
        this.questions = questions;
        this.zoneRoute = zoneRoute;
        this.currentStage = currentStage;
        this.playerResponses = playerResponses;
        this.numCorrect = numCorrect;

    }

    /**
     * private method for incrementing numCorrect
     */
    private void incrementNumCorrect() {
        this.numCorrect += 1;
    }

    /**
     * Checks response correctness. If correct, mark it correct and increment numCorrect.
     * @param question
     * @param response
     */
    private void validateResponse(Question question, Response response) {
        if (question.getSolution() == response.getResponseStr()) {
            response.markCorrect();
            incrementNumCorrect();
        }
    }

    /**
     * Adds response to playerResponse and checks if correct & incrementing score if correct
     * @param response
     */
    public void addResponse(Response response) {
        // check correctness of response.
        // by comparing the response with the question of the current stage
        validateResponse(questions.get(currentStage), response);
        this.playerResponses.add(response);
    }

    /**
     * Used by LocationController whenever player arrives in the correct zone
     */
    public void incrementCurrentStage() {
        this.currentStage += 1;
    }

    public Boolean isGameOver() {
        return playerResponses.size() == numQuestions;
    }


    // Getters and Setters


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Zone> getZoneRoute() {
        return zoneRoute;
    }

    public void setZoneRoute(ArrayList<Zone> zoneRoute) {
        this.zoneRoute = zoneRoute;
    }
    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public ArrayList<Response> getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(ArrayList<Response> playerResponses) {
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