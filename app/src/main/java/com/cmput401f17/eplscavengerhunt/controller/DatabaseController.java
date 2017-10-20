package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.List;

/**
 * Currently, DatabaseController handles retrieving the branch,
 * zones and questions in zones. It will possibly handle
 * adding respones to a qusetion to the database as well.
 * TODO: Make DatabaseController act as a client connecting to an api middleware
 */
public class DatabaseController {

    public DatabaseController() {
    }

    /**
     * Uses inputted GPS coordinates to retreive the name of the branch that
     * the coordinate is inside from the database.
     * @return String           Library branch name
     */
    public String retreiveBranch() {
        return null;
    }

    /**
     * Retrieves all Zones that are in the inputted library branch name.
     * NOTE: returned list's order is to be randomized in GameController
     * @see GameController
     * @param branch            The name of a library branch
     * @return List<Zone>       The zones in the inputted library branch
     */
    public List<Zone> retrieveZones(String branch){
        return null;
    }

    /**
     * Retrieves all questions relating to inputted zone.
     * Randomized selection handled by GameController
     * @see GameController
     * @param zone              A zone object containing beacon ids and questions
     * @return List<Question>   The questions pertaining to the zone
     */
    public List<Question> retrieveQuestionsinZone (Zone zone) {
        return null;
    }

    /**
     * (UNDECIDED FEATURE) Adds player response to Database.
     * Used in case response statistics becomes requirement for
     * system admin application.
     * @param response          The users response to a specific question
     */
    public void addResponse(Response response) {
    }
}
