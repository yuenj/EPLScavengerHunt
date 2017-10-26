package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class QuestionControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ScavHuntState mockScavHuntState;

    @Captor
    private ArgumentCaptor<Response> responseCaptor;

    QuestionController questionController;

    Question question1;
    Response response1;
    String answer1;

    @Before
    public void init() {
        questionController = new QuestionController(mockScavHuntState);

        question1 = mock(Question.class);
        response1 = mock(Response.class);
        answer1 = "ANSWER1";
    }

    @Test
    public void requestQuestionTest() {
        when(mockScavHuntState.getCurrentQuestion()).thenReturn(question1);

        Question returnedQuestion = questionController.requestQuestion();

        verify(mockScavHuntState, times(1)).getCurrentQuestion();
        assertEquals(question1, returnedQuestion);
    }

    @Test
    public void requestSubmitResponseTest() {
        questionController.requestSubmitResponse(answer1);

        verify(mockScavHuntState,times(1)).addResponse(responseCaptor.capture());

        Response responseSentToScavHuntState = responseCaptor.getValue();

        assertEquals(responseSentToScavHuntState.getResponseStr(), answer1);
    }

    @Test
    public void skipTest() {

        questionController.skip(question1);
        verify(question1,times(1)).skip();

    }


}
