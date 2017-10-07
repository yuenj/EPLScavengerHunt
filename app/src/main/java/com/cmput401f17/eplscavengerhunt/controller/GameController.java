package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.model.Results;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import android.app.Application;


import javax.inject.Inject;

public class GameController {
    @Inject
    ScavHuntState scavHuntState;


    public GameController() {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
    }

    public Boolean initGame() {
        return null;
    }

    public Results requestResults() {
        return null;
    }

    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }

    /**
     * Starts the QuestionActivity that corresponds to the current
     * question's type. Used by LocationController when user is
     * in the correct zone and is ready to receive a question.
     */
    public void startQuestionActivity() {
    }

    /**
     * Generates a sequence of zones in the branch in randomized order, storing it in
     * ScavHuntApplication.
     */
    private void generateZoneRoute() {

    }

    /**
     * Generates a sequence of questions. For each zone, one is randomly
     * selected (via DBController) from the pool of questions that correspond
     * to that zone. Each zone gets one question.
     */
    private void generateQuestionSet() {

    }
}
