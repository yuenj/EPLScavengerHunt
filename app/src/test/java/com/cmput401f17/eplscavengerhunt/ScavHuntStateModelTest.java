package com.cmput401f17.eplscavengerhunt;


import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ScavHuntStateModelTest {

    @Test
    public void generateResultsTest() {

    }

    @Test
    public void updateNumCorrectTest() {
        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setNumCorrect(0);

        testScavHuntState.incrementNumCorrect();
        int incrementedNumCorrect = testScavHuntState.getNumCorrect();

        assertEquals(1, incrementedNumCorrect);
    }

    @Test
    public void checkIfCorrectTest() {

    }

    @Test
    public void updateCurrentStageTest() {

    }

    @Test
    public void isGameOverTest() {

        String[] dummyResponses = {"Response1", "Response2", "Response3", "Response4", "Response5"};
        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setNumQuestions(5);
        testScavHuntState.setPlayerResponses(dummyResponses);


        Boolean testGameOver = testScavHuntState.isGameOver();

        assertTrue(testGameOver);

    }

}
