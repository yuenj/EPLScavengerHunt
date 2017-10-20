package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * Responsible for initializing a new game scenario, game logic
 * such as checking for gameOver and incrementing current stage
 * when user is ready to get the next zone, and generating summary
 * of the game upon gameOver.
 */
public class GameController {

    private ScavHuntState scavHuntState;
    private DatabaseController databaseController;

    @Inject
    public GameController(ScavHuntState scavHuntState, DatabaseController databaseController) {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        this.scavHuntState = scavHuntState;
        this.databaseController = databaseController;
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

    public Summary requestSummary() {
        return generateSummary();
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
     * Generates a sequence of questions. Randomization of question selection
     * is done here. For each zone, gets all questions in the question pool, and
     * randomly selects one. Each zone gets one question. Stores this series
     * of questions in ScavHuntState
     */
     void generateQuestionSet(List<Zone> zoneRoute) {
        List<Question> questionPool;
        List<Question> questionSet = new ArrayList<>();
        Random rand = new Random();

        for (Zone zone : zoneRoute) {
            questionPool = databaseController.retrieveQuestionsinZone(zone);
            Question randomQuestion = questionPool.get(rand.nextInt(questionPool.size()));
            questionSet.add(randomQuestion);
        }
        scavHuntState.setQuestions(questionSet);
    }

    private Summary generateSummary() {
        List<Response> responses = scavHuntState.getPlayerResponses();
        List<Question> questions = scavHuntState.getQuestions();
        int score = scavHuntState.getNumCorrect();
        int numQuestions = scavHuntState.getNumStages();

        Summary summary = new Summary();
        summary.setResponses(responses);
        summary.setScore(score);
        summary.setNumQuestions(numQuestions);
        summary.setQuestions(questions);

        return summary;
    }

    /**
     * Hard coded scavHuntState population
     */
    public void initScav() {

        // Clear previous game data if user just
        // completed a game, gets sent back to the TitleActivity
        // and chooses to start the game again.
        if (!scavHuntState.getBranch().equals("")) {
            scavHuntState.clearPreviousGameData();
        }

        scavHuntState.clearPreviousGameData();
        scavHuntState.setBranch("Clareview");

        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]"); // DJBeet
        zone1.setName("Zone 1");
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]"); // Candystore
        zone2.setName("Zone 2");
        testZoneRoute.add(zone1);
        testZoneRoute.add(zone2);
        scavHuntState.setZoneRoute(testZoneRoute);

        String questionStrDummy1 = "Question 1";
        int id1 = 0;
        String solutionStrDummy1 = "Solution 1";
        Question testQuestion1 = new Question(id1, questionStrDummy1, solutionStrDummy1);

        String questionStrDummy2 = "Question 2";
        int id2 = 1;
        String solutionStrDummy2 = "Solution 2";
        Question testQuestion2 = new Question(id2, questionStrDummy2, solutionStrDummy2);
        ArrayList<String> testChoices = new ArrayList<String>() {{
            add("Solution 1");
            add("Solution 2");
            add("Solution 3");
        }};
        testQuestion2.setChoices(testChoices);

        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion1);
        testQuestionList.add(testQuestion2);
        scavHuntState.setQuestions(testQuestionList);
        scavHuntState.setNumStages(testZoneRoute.size());


    }
}

