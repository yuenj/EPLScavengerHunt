package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import javax.inject.Inject;

/**
 * Responsible for initializing a new game scenario, game logic
 * such as checking for gameOver and incrementing current stage
 * when user is ready to get the next zone, and generating summary
 * of the game upon gameOver.
 */
public class GameController {

    private final ScavHuntState scavHuntState;
    private final DatabaseController databaseController;

    @Inject
    public GameController(final ScavHuntState scavHuntState, final DatabaseController databaseController) {
        this.scavHuntState = scavHuntState;
        this.databaseController = databaseController;
    }

    /**
     * Initializes the game when start is pressed
     * This method is not used for now as initScav is sufficient
     * for demo purposes and because this method relies on the
     * DatabaseController through generateZoneRoute
     * TODO: Call initScav or some other method to create our scavHuntState object
     *
     * @return Boolean          Returns true or false if the game was initiated correctly
     */
    public Boolean initGame() {
        String branch = scavHuntState.getBranch();

        List<Zone> zoneRoute = generateZoneRoute(branch);

        generateQuestionSet(zoneRoute);

        // Returns false if the zone route or the number of stages is zero
        return !(scavHuntState.getZoneRoute().size() == 0 || scavHuntState.getNumStages() == 0);
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
     * @return zoneRoute        The current order of zones the user will go to
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
     *
     * @param zoneRoute         A list of zones a user will go through
     */
    private void generateQuestionSet(List<Zone> zoneRoute) {
        List<Question> questionPool;
        List<Question> questionSet = new ArrayList<>();

        // Min and max refer to the maximum number of questions
        // int rand = ThreadLocalRandom.current().nextInt(0, max + 1);
        Random rand = new Random();

        // Calling for a query for X amount of zones will be
        // a bottleneck for speed
        // TODO: Change databaseController to retrieve all questions or limit the amount of database calls
        for (Zone zone : zoneRoute) {
            questionPool = databaseController.retrieveQuestionsinZone(zone);
            Question randomQuestion = questionPool.get(rand.nextInt(questionPool.size()));
            questionSet.add(randomQuestion);
        }

        scavHuntState.setQuestions(questionSet);
    }

    public Summary requestSummary() {
        return generateSummary();
    }

    /**
     * Generates a summary to be used for display
     *
     * @return Summary          Contains the end-state of a users game
     *                          Most importantly, this contains the answers correct and their responses
     */
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
     * @return Boolean          True if game is over, false otherwise
     */
    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }

    /**
     * Hard coded questions, answers and zones
     * for demo and testing purposes
     */
    public void initScav() {

        // Clear previous game data if user just
        // completed a game, gets sent back to the TitleActivity
        // and chooses to start the game again.
        if (!scavHuntState.getBranch().equals("")) {
            scavHuntState.clearPreviousGameData();
        }

        scavHuntState.setBranch("Clareview");

        // Sets a zone with it's specific name and beacon id
        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[751928d83a65b08296715acc49a52220]"); // DJBeet2
        zone1.setName("1");
        Zone zone2 = new Zone("[08a819724b9dabd0ca9e64c0fcb2722e]"); // DJBeet3
        zone2.setName("2");

//        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]"); // DJBeet
//        zone1.setName("1");
//        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]"); // Candystore
//        zone2.setName("2");
//        Zone zone3 = new Zone("[9a78af8c1252fcb37abefecbbbe7322a]"); // Lemonade
//        zone3.setName("3");

        // Create the zone route
        testZoneRoute.add(zone1);
        testZoneRoute.add(zone2);
        //testZoneRoute.add(zone3);
        scavHuntState.setZoneRoute(testZoneRoute);

//        // Create written answer questions
//        String questionStrDummy1 = "Question 1";
//        int id1 = 0;
//        String solutionStrDummy1 = "Solution 1";
//        Question testQuestion1 = new WrittenInputQuestion(id1, questionStrDummy1, "www.image1.com", solutionStrDummy1);

        String questionStrDummy2 = "Question 2";
        int id2 = 1;
        String solutionStrDummy2 = "Solution 2";
        Question testQuestion2 = new WrittenInputQuestion(id2, questionStrDummy2, "https://upload.wikimedia.org/wikipedia/commons/5/5d/Restless_flycatcher04.jpg", solutionStrDummy2);

        String questionStrDummy3 = "Question 3";
        int id3 = 2;
        String solutionStrDummy3 = "Solution 3";

        // Create multiple choice question
        ArrayList<String> testChoices = new ArrayList<String>() {{
            add("Solution 1");
            add("Solution 2");
            add("Solution 3");
        }};

        Question testQuestion3 = new PicInputQuestion(id3, questionStrDummy3, "https://i.pinimg.com/736x/8b/91/5b/8b915b06d3319f5c38656ded5e7bb355--cute-baby-owl-baby-owls.jpg", testChoices, solutionStrDummy3);

        // Create the question list
        List<Question> testQuestionList = new ArrayList<>();
        //testQuestionList.add(testQuestion1);
        testQuestionList.add(testQuestion2);
        testQuestionList.add(testQuestion3);

        scavHuntState.setQuestions(testQuestionList);
        scavHuntState.setNumStages(testZoneRoute.size());
    }
}