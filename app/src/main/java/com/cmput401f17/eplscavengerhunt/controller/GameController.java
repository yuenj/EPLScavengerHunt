package com.cmput401f17.eplscavengerhunt.controller;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GameController {
    @Inject
    ScavHuntState scavHuntState;

    public GameController() {
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
    }

    public boolean requestGameInit() {

        return(false);
    }

    public void requestResults() {
    }

    public void requestCheckGameOver() {
    }

    private boolean generateZoneRoute() {
        return(false);
    }

    /**
    private void generateQuestionZone(String branchID, Zone[] zones) {
    }
**/
    public String initGame() {
        return "Dagger is working!";
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

        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion1);
        testQuestionList.add(testQuestion2);
        scavHuntState.setQuestions(testQuestionList);
    }
}
