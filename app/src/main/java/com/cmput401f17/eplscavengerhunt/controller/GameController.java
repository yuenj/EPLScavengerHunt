package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.Arrays;
import java.util.List;

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
    public GameController(final ScavHuntState scavHuntState,
                          final DatabaseController databaseController) {
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
        final String branch = scavHuntState.getBranch();

        // TODO place 5 in a config file or somwhere else, don't hardcode it
        final List<Zone> zoneRoute = generateZoneRoute(branch, 5);
        generateQuestionSet(zoneRoute);

        // TODO catch BranchNotFoundException (see below TODO)
        // exception before doing this or .clear() in cleanState() will throw
        // Clear previous game data if user just completed a game,
        // gets sent back to the TitleActivity and chooses to start the game again.
        scavHuntState.cleanState();

        // TODO can DatabaseController throw a BranchNotFoundException
        // TODO instead of checking the return value like this
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
    private List<Zone> generateZoneRoute(final String branch, final int numZones) {
        final List<Zone> zoneRoute =
                databaseController.retrieveRandomZonesInBranch(branch, numZones);
        scavHuntState.setZoneRoute(zoneRoute);

        scavHuntState.setNumStages(zoneRoute.size());

        return zoneRoute;
    }

    /**
     * Generates a sequence of questions.
     *
     * @param zoneRoute A list of zones a user will go through
     */
    private void generateQuestionSet(final List<Zone> zoneRoute) {
        final List<Question> questions =
                databaseController.retrieveRandomQuestionsForZones(zoneRoute);
        scavHuntState.setQuestions(questions);
    }

    public Summary requestSummary() {
        return generateSummary();
    }

    /**
     * Generates a summary to be used for display
     *
     * @return Summary          Contains the end-state of a users game
     * Most importantly, this contains the answers correct and their responses
     */
    private Summary generateSummary() {
        final List<Response> responses = scavHuntState.getPlayerResponses();
        final List<Question> questions = scavHuntState.getQuestions();
        final int score = scavHuntState.getNumCorrect();
        final int numQuestions = scavHuntState.getNumStages();

        final Summary summary = new Summary(responses, questions, score, numQuestions);

        return summary;
    }

    /**
     * @return Boolean          True if game is over, false otherwise
     */
    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }

    /** Hard coded questions, answers and zones for demo purposes */
    public void initScav() {

        scavHuntState.cleanState();

        scavHuntState.setBranch("Clareview");

        // Sets a zone with it's specific name and beacon id
<<<<<<< HEAD
        List<Zone> testZoneRoute = new ArrayList<>();
        Zone zone1 = new Zone("[751928d83a65b08296715acc49a52220]"); // DJBeet2
        zone1.setName("1");
        zone1.setColour("#2196f3");
        Zone zone2 = new Zone("[08a819724b9dabd0ca9e64c0fcb2722e]"); // DJBeet3
        zone2.setName("2");
        zone2.setColour("#ffc107");

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
=======
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]", "1"); // DJBeet
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]", "2"); // CandyStore
        Zone zone3 = new Zone("[9a78af8c1252fcb37abefecbbbe7322a]", "3"); // Lemonade

        // Create the zone route
        List<Zone> zoneRoute = Arrays.asList(zone1, zone2, zone3);
        scavHuntState.setZoneRoute(zoneRoute);
        scavHuntState.setNumStages(3);

        // Create written answer questions
        Question question1 = new WrittenInputQuestion(0, "Question 1", "www.image1.com", "Solution One");
        Question question2 = new WrittenInputQuestion(1, "Question 2", "www.image2.com", "Solution Two");
        // Create pic multiple choice question
        List<String> choices = Arrays.asList("Solution 1", "Solution 2", "Solution 3");
        Question question3 = new PicInputQuestion(2, "Question 3", "www.image3.com", choices, "Solution Three");

        // Create the question list
        List<Question> questionList = Arrays.asList(question1, question2, question3);
        scavHuntState.setQuestions(questionList);
>>>>>>> 86cf7a53ef48e6a05c28a1f02ec50a14858d685a
    }
}