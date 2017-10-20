package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.DatabaseController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.Zone;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ScavHuntState mockScavHuntState;

    @InjectMocks
    QuestionController questionController;

    @Test
    public void requestQuestionTest() {
        /*
        Question question = questionController.requestQuestion();

        assertEquals(question.getQuestionID(), id);
        assertEquals(question.getQuestionPrompt(), prompt);
        */
    }


    @Test
    public void requestSubmitResponseTest() {

    }

    @Test
    public void skipTest() {
        Question question = questionController.requestQuestion();

        assertFalse(question.isSkipped());
    }

}
