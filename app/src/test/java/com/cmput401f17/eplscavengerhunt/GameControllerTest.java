package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.DatabaseController;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @Mock
    ScavHuntState mockScavHuntState;

    @Mock
    DatabaseController mockDatabaseController;

    @InjectMocks
    GameController gameController;

    private Response response1 = mock(Response.class);
    private Response response2 = mock(Response.class);
    private Response response3 = mock(Response.class);
    private Response response4 = mock(Response.class);
    private Response response5 = mock(Response.class);

    @Before
    public void init() {
        gameController = new GameController(mockScavHuntState,mockDatabaseController);
    }

    @Test
    public void initGameShouldInitializeScavHuntState() {
        // GIVEN
        final String branch = "testBranch";
        final Zone zone1 = new Zone("testBeaconId", "testZoneName", "testZoneArea");
        final Zone zone2 = new Zone("testBeaconId2", "testZoneName2", "testZoneArea");
        final List<Zone> zoneRoute = Arrays.asList(zone1, zone2);
        final Question question1 = new WrittenInputQuestion(0, "Question 1", "www.image1.com", "Solution 1");
        final Question question2 = new WrittenInputQuestion(1, "Question 2", "www.image2.com", "Solution 2");
        final List<Question> questions = Arrays.asList(question1, question2);
        when(mockScavHuntState.getBranch()).thenReturn(branch);
        doNothing().when(mockScavHuntState).setZoneRoute(any(List.class));
        doNothing().when(mockScavHuntState).setNumStages(any(int.class));
        doNothing().when(mockScavHuntState).setQuestions(any(List.class));
        when(mockScavHuntState.getZoneRoute()).thenReturn(zoneRoute);
        when(mockScavHuntState.getNumStages()).thenReturn(zoneRoute.size());

        // WHEN
        final boolean success = gameController.initGame();

        // THEN
        assertTrue(success);
    }

    @Test
    public void requestSummaryTest() {
        List<Response> responses = new ArrayList<>();
        Collections.addAll(responses,
                response1, response2, response3, response4, response5);
        int score = 5;
        int numStages = 5;
        when(mockScavHuntState.getPlayerResponses()).thenReturn(responses);
        when(mockScavHuntState.getNumCorrect()).thenReturn(score);
        when(mockScavHuntState.getNumStages()).thenReturn(numStages);

        Summary summaryToBeSent = gameController.requestSummary();

        assertEquals(summaryToBeSent.getResponses(), responses);
        assertEquals(summaryToBeSent.getScore(), score);
        assertEquals(summaryToBeSent.getNumQuestions(), numStages);
    }

    @Test
    public void requestCheckGameOverTestWhenGameOver() {
        when(mockScavHuntState.isGameOver()).thenReturn(true);

        Boolean gameOver = gameController.requestCheckGameOver();

        assertTrue(gameOver);
    }

    @Test
    public void requestCheckGameOverTestWhenNotGameOver() {
        when(mockScavHuntState.isGameOver()).thenReturn(false);

        Boolean gameOver = gameController.requestCheckGameOver();

        verify(mockScavHuntState).isGameOver();
        assertFalse(gameOver);
    }

    @Test
    public void requestIncrementCurrentStageTest() {
        gameController.requestIncrementCurrentStage();

        verify(mockScavHuntState, times(1)).incrementCurrentStage();
    }
}
