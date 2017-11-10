package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.Arrays;
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
        //FOR TESTING
        scavHuntState.setBranch("Clareview");

        String branch = scavHuntState.getBranch();
        final List<Zone> zoneRoute = generateZoneRoute(branch,5);

        // TODO place 5 in a config file or somwhere else, don't hardcode it
        //final List<Zone> zoneRoute = generateZoneRoute(branch, 5);
        generateQuestionSet(zoneRoute);

        // TODO catch BranchNotFoundException (see below TODO)
        // exception before doing this or .clear() in cleanState() will throw
        // Clear previous game data if user just completed a game,
        // gets sent back to the TitleActivity and chooses to start the game again.
        //scavHuntState.cleanState();

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
        //final List<Zone> zoneRoute = databaseController.retrieveRandomZonesInBranch(branch, numZones);
        List<Zone> zoneRoute = databaseController.retrieveZones(branch);
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
        //final List<Question> questions = databaseController.retrieveRandomQuestionsForZones(zoneRoute);
        //scavHuntState.setQuestions(questions);
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
     * Most importantly, this contains the answers correct and their responses
     */
    private Summary generateSummary() {
        final List<Response> responses = scavHuntState.getPlayerResponses();
        final List<Question> questions = scavHuntState.getQuestions();
        final List<Zone> zones = scavHuntState.getZoneRoute();
        final int score = scavHuntState.getNumCorrect();
        final int numQuestions = scavHuntState.getNumStages();

        final Summary summary = new Summary(responses, questions, zones, score, numQuestions);

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
        Zone zone1 = new Zone("[4f8113396f78d23ec78edfb96c79e23a]", "1", "Children's area: Birds"); // DJBeet
        Zone zone2 = new Zone("[ab1d6643c33e5f6ed7c52a062168f137]", "2", "Nature area"); // CandyStore
        Zone zone3 = new Zone("[9a78af8c1252fcb37abefecbbbe7322a]", "3", "Nature area"); // Lemonade
        // Give each zone a color
        zone1.setColor("#AA00AA00");
        zone2.setColor("#AAFFAA00");
        zone3.setColor("#84FFFF00");

        // Create the zone route
        List<Zone> zoneRoute = Arrays.asList(zone1, zone2, zone3);
        scavHuntState.setZoneRoute(zoneRoute);
        scavHuntState.setNumStages(3);

        // Create multiple choice question
        String question1Prompt = "This bird is 24 centimetres (9 inches) long and is easily identified by its long legs and short, barred tail. What bird am I?";
        String question1Solution = "Burrowing Owl";
        List<String> question1Choices = Arrays.asList("Peregrine Falcon", "Burrowing Owl", "Humming Bird", "Barn Owl");
        Question question1 = new MultipleChoiceQuestion(0, question1Prompt, "burrowing_owl", question1Choices, question1Solution);
        // Create written answer question
        String question2Prompt = "Great rivers that flowed here 75 million years ago left sand and mud deposits. What landscape of Alberta is this?";
        String question2Solution = "Badlands";
        Question question2 = new WrittenInputQuestion(1, question2Prompt, "badlands", question2Solution);
        // Create Picture with multiple choice question
        String question3Prompt = "Located in the Southwestern edge of Alberta, the ________________ are perhaps the best known and most well visited of Alberta's six natural regions.";
        String question3Solution = "Rocky Mountains";
        List<String> choices = Arrays.asList("Mount Everest", question3Solution, "Grand Canyon");
        Question question3 = new PicInputQuestion(3, question3Prompt, "rocky_mountains", choices, question3Solution);

        // Create the question list
        List<Question> questionList = Arrays.asList(question1, question2, question3);
        scavHuntState.setQuestions(questionList);
    }
}