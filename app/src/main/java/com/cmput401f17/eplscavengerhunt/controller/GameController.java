package com.cmput401f17.eplscavengerhunt.controller;

import android.util.Log;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * GameController handles the initialization, manipulation, and
 * progression of the game state and fetches data from it.
 */
public class GameController {

    private final ScavHuntState scavHuntState;
    private final DatabaseController databaseController;

    @Inject
    public GameController(final ScavHuntState scavHuntState,
                          final DatabaseController databaseController) {
        this.scavHuntState = scavHuntState;
        this.databaseController = databaseController;
    }

    /**
     * Initializes the game state. Returns true on successful initialization.
     */
    public Boolean initGame() {
        scavHuntState.cleanState();
        scavHuntState.setBranch("Clareview");

        String branch = scavHuntState.getBranch();
        final List<Zone> zoneRoute = generateZoneRoute(branch);
        scavHuntState.setZoneRoute(zoneRoute);
        if (zoneRoute.size() > 0) {
            List<Question> questions = generateQuestionSet(zoneRoute);
            if (questions.size() > 0) {
                scavHuntState.setQuestions(questions);
                scavHuntState.setNumStages(questions.size());
                return true;
            } else {
                Log.d("DEBUG", "no questions drawn from the database");
                return false;
            }
        } else {
            Log.d("DEBUG", "no zones drawn from the database");
            return false;
        }
    }

    /**
     * generate a sequence of zones belonging to the branch
     */
    private List<Zone> generateZoneRoute(final String branch) {
        return databaseController.retrieveZones(branch);
    }

    /**
     * Generates a sequence of questions.
     */
    private List<Question> generateQuestionSet(final List<Zone> zoneRoute) {
        List<Question> questionSet = new ArrayList<>();

        Random rand = new Random();
        List<Question> questionPool;
        for (Zone zone : zoneRoute) {
            questionPool = databaseController.retrieveQuestionsInZone(zone);
            if (questionPool.size() > 0) {
                Question randomQuestion = questionPool.get(rand.nextInt(questionPool.size()));
                questionSet.add(randomQuestion);
            } else {
                // skip over this zone if there are no questions belonging to it
            }
        }
        return questionSet;
    }

    /**
     * Increments the current stage
     */
    public void requestIncrementCurrentStage() {
        scavHuntState.incrementCurrentStage();
    }

    /**
     * Generates a summary of the game results
     */
    public Summary requestSummary() {
        final List<Response> responses = scavHuntState.getPlayerResponses();
        final List<Question> questions = scavHuntState.getQuestions();
        final List<Zone> zones = scavHuntState.getZoneRoute();
        final int score = scavHuntState.getNumCorrect();
        final int numQuestions = scavHuntState.getNumStages();

        return new Summary(responses, questions, zones, score, numQuestions);
    }

    /**
     * Checks if the game is over
     */
    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }
}