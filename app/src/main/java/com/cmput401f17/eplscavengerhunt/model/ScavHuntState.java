package com.cmput401f17.eplscavengerhunt.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistent application game state.
 * Stores game progress and contains game logic.
 */
public class ScavHuntState {

    private String branch;
    private List<Question> questions;
    private List<Zone> zoneRoute;
    private int currentStage;
    private List<Response> playerResponses;
    private int numCorrect;
    private int numStages;

    public ScavHuntState() {
        cleanState();
    }

    private void incrementNumCorrect() {
        this.numCorrect++;
    }

    /**
     * Checks response correctness.
     * If correct, mark it correct and increment numCorrect.
     * @param question
     * @param response
     */
    private void validateResponse(final Question question, final Response response) {
        final String answer = question.getAnswer();

        // Compare answer and response and update score and response accordingly
        if (answer.equals(response.getResponseStr())) {
            response.markCorrect();
            incrementNumCorrect();
        }
        else {
            response.markIncorrect();
        }
    }

    /**
     * Checks response correctness and adds to playerResponses.
     * @param response
     */
    public void addResponse(final Response response) {
        // check correctness of response.
        // by comparing the response with the question of the current stage
        validateResponse(questions.get(currentStage), response);
        this.playerResponses.add(response);
    }

    /**
     * Used by LocationController whenever player arrives in the correct zone
     */
    public void incrementCurrentStage() {
        this.currentStage++;
    }

    /**
     * When submitted responses equals number of questions, it is game over.
     * @return Boolean True if gameOver, False if not
     */
    public Boolean isGameOver() {
        Log.d("Number of User Res",Integer.toString(playerResponses.size()));
        return playerResponses.size() == numStages;
    }

    public void cleanState() {
        this.branch = "";
        this.questions = new ArrayList<>();
        this.zoneRoute = new ArrayList<>();
        this.currentStage = 0;
        this.playerResponses = new ArrayList<>();
        this.numCorrect = 0;
        this.numStages = 0;
    }

    // Getters and Setters
    public String getBranch() {
        return branch;
    }

    public void setBranch(final String branch) {
        this.branch = branch;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentStage);
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

    public List<Zone> getZoneRoute() {
        return zoneRoute;
    }

    public Zone getCurrentZone() {
        return zoneRoute.get(currentStage);
    }

    public void setZoneRoute(final List<Zone> zoneRoute) {
        this.zoneRoute = zoneRoute;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(final int currentStage) {
        this.currentStage = currentStage;
    }

    public List<Response> getPlayerResponses() {
        return playerResponses;
    }

    public Response getCurrentResponse() {
        return playerResponses.get(currentStage);
    }

    public void setPlayerResponses(final List<Response> playerResponses) {
        this.playerResponses = playerResponses;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(final int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumStages() {
        return numStages;
    }

    public void setNumStages(final int numStages) {
        this.numStages = numStages;
    }
}