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
     * Retreives all Zones that are in the inputted library branch name.
     * NOTE: returned list's order is to be randomized in GameController
     * @see GameController
     * @param branch : the branch name the player is in
     * @return list of all zones in the branch
     */
    public List<Zone> retreiveZones(String branch){
        return null;
    }

    /**
     * Retreives random question from database's question pool
     * corresponding to zone
     * @param zone
     * @return
     */
    public Question retreiveRandomQuestion(Zone zone) {
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
