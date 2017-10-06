package com.cmput401f17.eplscavengerhunt;



import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

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
        Question testQuestion = mock(Question.class);
        Response testResponse = mock(Response.class);
        when(testQuestion.getSolution()).thenReturn("Dog!");
        when(testResponse.getResponseStr()).thenReturn("Dog!");
        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion);
        testScavHuntState.setQuestions(testQuestionList);

        testScavHuntState.addResponse(testResponse);

        int score = testScavHuntState.getNumCorrect();
        List<Question> stateQList = testScavHuntState.getQuestions();
        List<Response> stateRList = testScavHuntState.getPlayerResponses();

        assertTrue((score == 1) && (stateQList.get(0).getSolution()==stateRList.get(0).getResponseStr()));
    }

    @Test
    public void addIncorrectResponseTest() {
        ScavHuntState testScavHuntState = new ScavHuntState();
        Question testQuestion = mock(Question.class);
        Response testResponse = mock(Response.class);
        when(testQuestion.getSolution()).thenReturn("Dog!");
        when(testResponse.getResponseStr()).thenReturn("Cat!");
        List<Question> testQuestionList = new ArrayList<>();
        testQuestionList.add(testQuestion);
        testScavHuntState.setQuestions(testQuestionList);

        testScavHuntState.addResponse(testResponse);

        int score = testScavHuntState.getNumCorrect();
        List<Question> stateQList = testScavHuntState.getQuestions();
        List<Response> stateRList = testScavHuntState.getPlayerResponses();

        assertTrue((score == 0) && (stateQList.get(0).getSolution()!=stateRList.get(0).getResponseStr()));
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
        when(dummyResponse1.getResponseStr()).thenReturn("Response1!");
        when(dummyResponse1.getResponseStr()).thenReturn("Response2!");
        dummyResponses.add(dummyResponse1);
        dummyResponses.add(dummyResponse2);

        ScavHuntState testScavHuntState = new ScavHuntState();
        testScavHuntState.setNumQuestions(2);
        testScavHuntState.setPlayerResponses(dummyResponses);


        Boolean testGameOver = testScavHuntState.isGameOver();

        assertTrue(testGameOver);
    }


}
