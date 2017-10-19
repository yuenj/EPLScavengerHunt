package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Results;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GameController {

    @Inject
    ScavHuntState scavHuntState;

    @Inject
    DatabaseController databaseController;


    public GameController() {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
    }

    public Boolean initGame() {
        // branch is updated by LocationController
        String branch = scavHuntState.getBranch();

        // Generate zone route for ScavHuntState, but
        // also keep ZoneRoute for generateQuestionSet to use
        List<Zone> zoneRoute = generateZoneRoute(branch);

        generateQuestionSet(zoneRoute);

        // return True if successful, false otherwise
        // TitleActivity uses this boolean to throw a toast
        // for failure, or start the game if successful
        if (scavHuntState.getZoneRoute().size() == 0
                || scavHuntState.getNumStages() == 0) {
            return false;
        }
        return true;
    }

    public Results requestResults() {
        return generateResults();
    }

    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }

    /**
     * Increments current stage.
     * Called when user submits a question (called by QuestionActivity after submission)
     */
    public void requestIncrementCurrentStage() {
        scavHuntState.incrementCurrentStage();
    }

    /**
     * Starts the QuestionActivity that corresponds to the current
     * question's type. Used by LocationController when user is
     * in the correct zone and is ready to receive a question.
     */
    public void startQuestionActivity() {
        // TODO: IMPLEMENT when question subclasses & their activities are implemented
    }

    /**
     * Generates a sequence of zones in the branch in randomized order, storing it in
     * ScavHuntApplication. Since num Zones = num Questions, also updates numStages
     * in ScavHuntState
     *
     * @return the zone route passed to scavHuntState
     */
    private List<Zone> generateZoneRoute(String branch) {
        List<Zone> zoneRoute = databaseController.retrieveZones(branch);
        scavHuntState.setZoneRoute(zoneRoute);

        scavHuntState.setNumStages(zoneRoute.size());

        return zoneRoute;
    }

    /**
     * Generates a sequence of questions. For each zone, one is randomly
     * selected (via DBController) from the pool of questions that correspond
     * to that zone. Each zone gets one question. Stores this in ScavHuntState
     */
    private void generateQuestionSet(List<Zone> zoneRoute) {
        // TODO: IMPLEMENT
    }

    private Results generateResults() {
        List<Response> responses = scavHuntState.getPlayerResponses();
        List<Question> questions = scavHuntState.getQuestions();
        int score = scavHuntState.getNumCorrect();
        int numQuestions = scavHuntState.getNumStages();

        Results results = new Results();
        results.setResponses(responses);
        results.setScore(score);
        results.setNumQuestions(numQuestions);
        results.setQuestions(questions);

        return results;
    }

    /**
     * Hard coded scavHuntState
     */
    public void initScav() {
        scavHuntState.setBranch("Clareview");

        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]"); // DJBeet
        zone1.setName("Zone 1");
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]"); // Candystore
        zone2.setName("Zone 2");
        Zone zone3 = new Zone("abcdefghijklmnop"); // Candystore
        zone3.setName("Zone 3");
        testZoneRoute.add(zone1);
        testZoneRoute.add(zone2);
        testZoneRoute.add(zone3);
        scavHuntState.setZoneRoute(testZoneRoute);

        String questionStrDummy1 = "Question 1?";
        int id1 = 0;
        String solutionStrDummy1 = "Solution 1";
        Question testQuestion1 = new Question(id1, questionStrDummy1, solutionStrDummy1);

        String questionStrDummy2 = "Question 2";
        int id2 = 1;
        String solutionStrDummy2 = "Solution 2";
        Question testQuestion2 = new Question(id2, questionStrDummy2, solutionStrDummy2);

        String questionStrDummy3 = "Question 3";
        int id3 = 2;
        String solutionStrDummy3 = "Solution 3";
        Question testQuestion3 = new Question(id3, questionStrDummy3, solutionStrDummy3);

        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion1);
        testQuestionList.add(testQuestion2);
        testQuestionList.add(testQuestion3);

        scavHuntState.setQuestions(testQuestionList);
        scavHuntState.setNumStages(testZoneRoute.size());


    }
}

