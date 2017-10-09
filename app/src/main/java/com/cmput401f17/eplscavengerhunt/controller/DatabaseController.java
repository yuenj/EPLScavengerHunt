package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.List;

/**
 * Controller that handles all Database interaction needed
 * for this application.
 */
public class DatabaseController {

    public DatabaseController() {
    }

    /**
     * Uses inputted GPS coordinates to retreive the name of the branch that
     * the coordinate is inside from the database.
     * @return branch name
     */
    public String retreiveBranch() {
        return null;
    }

    /**
     * Retrieves all Zones that are in the inputted library branch name.
     * NOTE: returned list's order is to be randomized in GameController
     * @see GameController
     * @param branch : the branch name the player is in
     * @return list of all zones in the branch
     */
    public List<Zone> retrieveZones(String branch){
        return null;
    }

    /**
     * Retrieves all questions relating to inputted zone.
     * Randomized selection handled by GameController
     * @see GameController
     * @param zone
     * @return list of Questions relating to the zone
     */
    public List<Question> retrieveQuestionsinZone (Zone zone) {
        return null;
    }

    /**
     * (UNDECIDED FEATURE) Adds player response to Database.
     * Used in case response statistics becomes requirement for
     * system admin application.
     * @param response
     */
    public void addResponse(Response response) {
    }
}
