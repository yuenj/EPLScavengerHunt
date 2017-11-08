package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.List;

/**
 * Currently, DatabaseController handles retrieving the branch,
 * zones and questions in zones. It will possibly handle
 * adding responses to a question to the database as well.
 * TODO: Make DatabaseController act as a client connecting to an api middleware
 */
public class DatabaseController {

    public DatabaseController() {
    }

    /*** Returns a random list of unique [numZones] zones belonging to a library [branch)] */
    public List<Zone> retrieveRandomZonesInBranch(final String branch, final int numQuestions) {
        return null;
    }

    /** Given a list of [zones], returns a random list of unique questions
     *  belonging to that zone.
     */
    public List<Question> retrieveRandomQuestionsForZones(final List<Zone> zones) {
        return null;
    }

    /**
     * Uses inputted GPS coordinates to retrieve the name of the branch that
     * the coordinate is inside from the database.
     *
     * @return String           Library branch name
     */
    public String retrieveBranch() {
        return null;
    }

    /**
     * Retrieves all Zones that are in the inputted library branch name.
     * NOTE: returned list's order is to be randomized in GameController
     *
     * @param branch            The name of a library branch
     * @return List<Zone>       The zones in the inputted library branch
     * @see GameController
     */
    public List<Zone> retrieveZonesInBranch(String branch) {
        return null;
    }

    /**
     * Retrieves all questions relating to inputted zone.
     * Randomized selection handled by GameController
     *
     * @param zone              A zone object containing beacon ids and questions
     * @return List<Question>   The questions pertaining to the zone
     * @see GameController
     */
    public List<Question> retrieveQuestionsinZone(Zone zone) {
        return null;
    }

    /**
     * (UNDECIDED FEATURE) Adds player response to Database.
     * Used in case response statistics becomes requirement for
     * system admin application.
     *
     * @param response          The users response to a specific question
     */
    public void addResponse(Response response) {}
}
