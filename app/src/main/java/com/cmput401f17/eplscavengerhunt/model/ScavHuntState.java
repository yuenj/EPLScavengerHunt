package com.cmput401f17.eplscavengerhunt.model;


import java.util.ArrayList;
import java.util.List;

/**
 * persistent application game state. stores game progress and contains
 * game logic.
 */
public class ScavHuntState {
    private String branch;
    private List<Question> questions;
    private List<Zone> zoneRoute;
    private int currentStage;
    private List<Response> playerResponses;
    private int numCorrect;
    private int numStages = 0;


    public ScavHuntState() {
        this.branch = "";
        this.questions = new ArrayList<>();
        this.zoneRoute = new ArrayList<>();
        this.currentStage = 0;
        this.playerResponses = new ArrayList<>();
        this.numCorrect = 0;
        this.numStages = 0;
    }

    public ScavHuntState(String branch, List<Question> questions, List<Zone> zoneRoute, int currentStage, List<Response> playerResponses, int numCorrect) {
        this.branch = branch;
        this.questions = questions;
        this.zoneRoute = zoneRoute;
        this.currentStage = currentStage;
        this.playerResponses = playerResponses;
        this.numCorrect = numCorrect;
    }


    private void incrementNumCorrect() {
        this.numCorrect += 1;
    }

    /**
     * private method for checking response correctness.
     * If correct, mark it correct and increment numCorrect.
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
     * Checks response correctness and adds
     * to playerResponses.
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

    /**
     * When submitted responses equals number of questions, it is game over.
     * @return Boolean True if gameOver, False if not
     */
    public Boolean isGameOver() {
        return playerResponses.size() == numStages;
    }


    // Getters and Setters
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentStage);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Zone> getZoneRoute() {
        return zoneRoute;
    }

    public Zone getCurrentZone() {
        return zoneRoute.get(currentStage);
    }

    public void setZoneRoute(List<Zone> zoneRoute) {
        this.zoneRoute = zoneRoute;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public List<Response> getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(List<Response> playerResponses) {
        this.playerResponses = playerResponses;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumStages() {
        return numStages;
    }

    public void setNumStages(int numStages) {
        this.numStages = numStages;
    }
}