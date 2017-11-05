package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.DatabaseController;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import static org.mockito.Mockito.*;

import org.junit.Before;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @Mock
    ScavHuntState mockScavHuntState;

    @Mock
    DatabaseController mockDatabaseController;

    @Captor
    private ArgumentCaptor<List<Question>> QuestionSetCaptor;

    @Captor
    private ArgumentCaptor<List<Zone>> ZoneCaptor;

    @Captor
    private ArgumentCaptor<Response> ResponseCaptor;

    @Captor
    private ArgumentCaptor<Integer> IntegerCaptor;

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
        when(mockDatabaseController.retrieveRandomZonesInBranch(
                any(String.class), any(int.class))).thenReturn(zoneRoute);
        doNothing().when(mockScavHuntState).setZoneRoute(any(List.class));
        doNothing().when(mockScavHuntState).setNumStages(any(int.class));
        doNothing().when(mockScavHuntState).cleanState();
        when(mockDatabaseController.retrieveRandomQuestionsForZones(any(List.class))).thenReturn(questions);
        doNothing().when(mockScavHuntState).setQuestions(any(List.class));
        when(mockScavHuntState.getZoneRoute()).thenReturn(zoneRoute);
        when(mockScavHuntState.getNumStages()).thenReturn(zoneRoute.size());

        // WHEN
        final boolean success = gameController.initGame();

        // THEN
        assertTrue(success);
    }

// TODO move this test into backend associated with db
//    private Zone zone1 = mock(Zone.class);
//    private Zone zone2 = mock(Zone.class);
//    private Zone zone3 = mock(Zone.class);
//    private Zone zone4 = mock(Zone.class);
//    private Zone zone5 = mock(Zone.class);
//
//    private WrittenInputQuestion question1 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question2 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question3 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question4 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question5 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question6 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question7 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question8 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question9 = mock(WrittenInputQuestion.class);
//    private WrittenInputQuestion question10 = mock(WrittenInputQuestion.class);
//    @Test
//    /**
//     * retreiveRandomQuestions and generateZoneRoute are private methods
//     * so cannot be tested
//     * ** ZONE GENERATION related tests already pass **
//     * Verify that:
//     * - getBranch() called on ScavHuntState
//     * - retreiveZones and retreiveRandomQuestions called on
//     *   DatabaseController
//     * - setQuestions, setZoneRoute, setNumStages have been called
//     * exactly once each on ScavHuntState
//     *
//     * Capture:
//     * - zoneRoute and questionSet and numStages sent to mock scavHuntState.
//     */
//    public void initGameTest() {
//        // mock situation: 5 zones, each zone has question pools of size 2
//        List<Zone> zoneRoute = new ArrayList<>();
//        Collections.addAll(zoneRoute, zone1, zone2, zone3, zone4, zone5);
//        List<Question> questionPool1 = new ArrayList<>();
//        List<Question> questionPool2 = new ArrayList<>();
//        List<Question> questionPool3 = new ArrayList<>();
//        List<Question> questionPool4 = new ArrayList<>();
//        List<Question> questionPool5 = new ArrayList<>();
//        Collections.addAll(questionPool1,
//                question1, question2);
//        Collections.addAll(questionPool2,
//                question3, question4);
//        Collections.addAll(questionPool3,
//                question5, question6);
//        Collections.addAll(questionPool4,
//                question7, question8);
//        Collections.addAll(questionPool5,
//                question9, question10);
//        when(mockDatabaseController
//                .retrieveRandomZonesInBranch("Clareview", 5)).thenReturn(zoneRoute);
//        when(mockDatabaseController
//                .retrieveQuestionsinZone(zone1)).thenReturn(questionPool1);
//        when(mockDatabaseController
//                .retrieveQuestionsinZone(zone2)).thenReturn(questionPool2);
//        when(mockDatabaseController
//                .retrieveQuestionsinZone(zone3)).thenReturn(questionPool3);
//        when(mockDatabaseController
//                .retrieveQuestionsinZone(zone4)).thenReturn(questionPool4);
//        when(mockDatabaseController.
//                retrieveQuestionsinZone(zone5)).thenReturn(questionPool5);
//
//        when(mockScavHuntState.getBranch()).thenReturn("Clareview");
//
//        gameController.initGame();
//
//        verify(mockScavHuntState).getBranch();
//        verify(mockDatabaseController).retrieveRandomZonesInBranch("Clareview", 5);
//
//        // verify question pools from all 5 zones is pulled from database
//        verify(mockDatabaseController).retrieveQuestionsinZone(zone1);
//        verify(mockDatabaseController).retrieveQuestionsinZone(zone2);
//        verify(mockDatabaseController).retrieveQuestionsinZone(zone3);
//        verify(mockDatabaseController).retrieveQuestionsinZone(zone4);
//        verify(mockDatabaseController).retrieveQuestionsinZone(zone5);
//
//
//        verify(mockScavHuntState, times(1)).setQuestions(QuestionSetCaptor.capture());
//        verify(mockScavHuntState, times(1)).setNumStages(IntegerCaptor.capture());
//        verify(mockScavHuntState, times(1)).setZoneRoute(ZoneCaptor.capture());
//
//        // capture values
//        List<Question> questionsSetSentToScavHuntState = QuestionSetCaptor.getValue();
//        List<Zone> zoneRouteSentToScavHuntState = ZoneCaptor.getValue();
//        Integer numStagesSentToScavHuntState = IntegerCaptor.getValue();
//
//        assertEquals(zoneRoute, zoneRouteSentToScavHuntState);
//
//        // assert a question from all pools is in question set
//        Boolean QuestionFromPool1Picked = !(Collections.disjoint(
//                questionsSetSentToScavHuntState, questionPool1));
//        Boolean QuestionFromPool2Picked = !(Collections.disjoint(
//                questionsSetSentToScavHuntState, questionPool2));
//        Boolean QuestionFromPool3Picked = !(Collections.disjoint(
//                questionsSetSentToScavHuntState, questionPool3));
//        Boolean QuestionFromPool4Picked = !(Collections.disjoint(
//                questionsSetSentToScavHuntState, questionPool4));
//        Boolean QuestionFromPool5Picked = !(Collections.disjoint(
//                questionsSetSentToScavHuntState, questionPool5));
//        assertTrue(QuestionFromPool1Picked);
//        assertTrue(QuestionFromPool2Picked);
//        assertTrue(QuestionFromPool3Picked);
//        assertTrue(QuestionFromPool4Picked);
//        assertTrue(QuestionFromPool5Picked);
//
//        assertEquals(numStagesSentToScavHuntState,(Integer) 5);
//
//        // note: if questions have been picked from all question pools,
//        // and number of stage
//    }


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
