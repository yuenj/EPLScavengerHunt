package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.DatabaseController;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Results;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import static org.mockito.Mockito.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ScavHuntState mockScavHuntState;

    @Mock
    DatabaseController mockDatabaseController;

    @InjectMocks
    GameController gameController;

    @Captor
    private ArgumentCaptor<List<Question>> QuestionSetCaptor;

    @Captor
    private ArgumentCaptor<List<Zone>> ZoneCaptor;

    @Captor
    private ArgumentCaptor<Response> ResponseCaptor;

    Zone zone1 = mock(Zone.class);
    Zone zone2 = mock(Zone.class);
    Zone zone3 = mock(Zone.class);
    Zone zone4 = mock(Zone.class);
    Zone zone5 = mock(Zone.class);

    Question question1 = mock(Question.class);
    Question question2 = mock(Question.class);
    Question question3 = mock(Question.class);
    Question question4 = mock(Question.class);
    Question question5 = mock(Question.class);

    Response response1 = mock(Response.class);
    Response response2 = mock(Response.class);
    Response response3 = mock(Response.class);
    Response response4 = mock(Response.class);
    Response response5 = mock(Response.class);


    @Test
    /**
     * Verify that:
     * - getBranch() called on ScavHuntState
     * - retreiveZones and retreiveRandomQuestions called on
     *   DatabaseController
     * - setQuestions and setZoneRoute have been called
     * exactly once each on ScavHuntState
     *
     * Capture:
     * - zoneRoute and questionSet sent to mock scavHuntState.
     */
    public void initGameTest() {
        List<Zone> zoneRoute = new ArrayList<>();
        Collections.addAll(zoneRoute, zone1, zone2, zone3, zone4, zone5);
        List<Question> questionSet = new ArrayList<>();
        Collections.addAll(questionSet,
                question1, question2, question3, question4, question5);
        when(mockDatabaseController.retreiveZones("Clareview")).thenReturn(zoneRoute);
        when(mockDatabaseController.retreiveRandomQuestions(zoneRoute)).thenReturn(questionSet);
        when(mockScavHuntState.getBranch()).thenReturn("Clareview");

        gameController.initGame();

        verify(mockScavHuntState).getBranch();
        verify(mockDatabaseController).retreiveZones("Clareview");
        verify(mockDatabaseController).retreiveRandomQuestions(zoneRoute);
        verify(mockScavHuntState, times(1)).setQuestions(QuestionSetCaptor.capture());
        verify(mockScavHuntState, times(1)).setZoneRoute(ZoneCaptor.capture());

        List<Question> questionsSetSentToScavHuntState = QuestionSetCaptor.getValue();
        List<Zone> zoneRouteSentToScavHuntState = ZoneCaptor.getValue();

        assertEquals(zoneRoute, zoneRouteSentToScavHuntState);
        assertEquals(questionSet,questionsSetSentToScavHuntState);
    }

    @Test
    public void requestResultsTest() {
        List<Response> responses = new ArrayList<>();
        Collections.addAll(responses,
                response1, response2, response3, response4, response5);
        int score = 5;
        int numQuestions = 5;
        when(mockScavHuntState.getPlayerResponses()).thenReturn(responses);
        when(mockScavHuntState.getNumCorrect()).thenReturn(score);
        when(mockScavHuntState.getNumQuestions()).thenReturn(numQuestions);

        Results resultsToBeSent = gameController.requestResults();

        assertEquals(resultsToBeSent.getResponses(), responses);
        assertEquals(resultsToBeSent.getScore(), score);
        assertEquals(resultsToBeSent.getNumQuestions(), numQuestions);
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
    public void startQuestionActivityTest() {
    }
}