package com.cmput401f17.eplscavengerhunt.controller;

import android.widget.Toast;

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
     *
     * @return Boolean          Returns true or false if the game was initiated correctly
     */
    public Boolean initGame() {
        scavHuntState.cleanState();
        scavHuntState.setBranch("Clareview"); // Hardcoded branch for testing

        String branch = scavHuntState.getBranch();
        final List<Zone> zoneRoute = generateZoneRoute(branch,5);
        if (zoneRoute.size() > 0) {
            System.out.println("IT WORKSADAWDAWDAW");
        }

        /* No data drawn. Return to restart */
        if(zoneRoute.isEmpty()) {
            return (false);
        }

        // TODO place 5 in a config file or somwhere else, don't hardcode it
        generateQuestionSet(zoneRoute);

        // TODO: Change this when branch detection is implemented
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

        if(zoneRoute == null || zoneRoute.isEmpty()) {
            //Set empty list as there are no null checks anywhere else
            zoneRoute = Arrays.asList();
            scavHuntState.setNumStages(0);
        } else {
            scavHuntState.setNumStages(zoneRoute.size());
        }

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

        Random rand = new Random();

        for (Zone zone : zoneRoute) {
            questionPool = databaseController.retrieveQuestionsinZone(zone);
            Question randomQuestion = questionPool.get(rand.nextInt(questionPool.size()));
            questionSet.add(randomQuestion);
        }
        scavHuntState.setQuestions(questionSet);
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


    public Summary requestSummary() {
        return generateSummary();
    }

    /**
     * @return Boolean          True if game is over, false otherwise
     */
    public Boolean requestCheckGameOver() {
        return scavHuntState.isGameOver();
    }
}