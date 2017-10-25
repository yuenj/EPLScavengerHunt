package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.WrittenInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import org.junit.Rule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScavHuntStateModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    // Check that when addResponse adds correct answer, correctly
    // increments player score & response corresponds to question's solution
    public void addCorrectResponseTest() {
        ScavHuntState testScavHuntState = new ScavHuntState();
        WrittenInputQuestion testQuestion = mock(WrittenInputQuestion.class);
        Response testResponse = mock(Response.class);
        when(testQuestion.getWrittenInputSolution()).thenReturn("Dog!");
        when(testResponse.getResponseStr()).thenReturn("Dog!");
        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion);
        testScavHuntState.setQuestions(testQuestionList);

        testScavHuntState.addResponse(testResponse);

        int score = testScavHuntState.getNumCorrect();
        List<Question> stateQList = testScavHuntState.getQuestions();
        List<Response> stateRList = testScavHuntState.getPlayerResponses();

        assertTrue((score == 1) && (((WrittenInputQuestion)stateQList.get(0)).getWrittenInputSolution()==stateRList.get(0).getResponseStr()));
    }

    @Test
    public void addIncorrectResponseTest() {
        ScavHuntState testScavHuntState = new ScavHuntState();
        WrittenInputQuestion testQuestion = mock(WrittenInputQuestion.class);
        Response testResponse = mock(Response.class);
        when(testQuestion.getWrittenInputSolution()).thenReturn("Dog!");
        when(testResponse.getResponseStr()).thenReturn("Cat!");
        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion);
        testScavHuntState.setQuestions(testQuestionList);

        testScavHuntState.addResponse(testResponse);

        int score = testScavHuntState.getNumCorrect();
        List<Question> stateQList = testScavHuntState.getQuestions();
        List<Response> stateRList = testScavHuntState.getPlayerResponses();

        assertTrue((score == 0) && (((WrittenInputQuestion)stateQList.get(0)).getWrittenInputSolution()!=stateRList.get(0).getResponseStr()));
    }


    @Test
    public void incrementCurrentStageTest() {
        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setCurrentStage(0);

        testScavHuntState.incrementCurrentStage();
        int incrementedCurrentStage = testScavHuntState.getCurrentStage();

        assertEquals(1, incrementedCurrentStage);
    }

    @Test
    public void isGameOverTest() {
        List<Response> dummyResponses = new ArrayList<>();
        Response dummyResponse1 = mock(Response.class);
        Response dummyResponse2 = mock(Response.class);
        dummyResponses.add(dummyResponse1);
        dummyResponses.add(dummyResponse2);

        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setNumStages(2);
        testScavHuntState.setPlayerResponses(dummyResponses);


        Boolean testGameOver = testScavHuntState.isGameOver();

        assertTrue(testGameOver);
    }

    @Test
    public void getCurrentQuestionTest() {
        List<Question> dummyQuestions = new ArrayList<>();
        Question mockQuestion1 = mock(Question.class);
        Question mockQuestion2 = mock(Question.class);
        dummyQuestions.add(mockQuestion1);
        dummyQuestions.add(mockQuestion2);
        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setQuestions(dummyQuestions);
        testScavHuntState.setNumStages(2);
        testScavHuntState.setCurrentStage(1);

        Question returnedQuestion = testScavHuntState.getCurrentQuestion();

        assertTrue(returnedQuestion == mockQuestion2);
    }

    @Test
    public void getCurrentZoneTest() {
        List<Zone> dummyZones = new ArrayList<>();
        Zone mockZone1 = mock(Zone.class);
        Zone mockZone2 = mock(Zone.class);
        dummyZones.add(mockZone1);
        dummyZones.add(mockZone2);
        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setZoneRoute(dummyZones);
        testScavHuntState.setCurrentStage(1);

        Zone returnedZone = testScavHuntState.getCurrentZone();

        assertTrue(returnedZone == mockZone2);
    }
}
