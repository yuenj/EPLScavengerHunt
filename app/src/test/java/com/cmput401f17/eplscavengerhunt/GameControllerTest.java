package com.cmput401f17.eplscavengerhunt;

import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import static org.mockito.Mockito.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ScavHuntState scavHuntState;

    @InjectMocks
    GameController gameController;

    @Test
    public void initGameTest() {

    }

    @Test
    public void requestResultsTest() {

    }

    @Test
    public void requestCheckGameOverTestWhenGameOver() {
        when(scavHuntState.isGameOver()).thenReturn(true);

        Boolean gameOver = gameController.requestCheckGameOver();

        assertTrue(gameOver);
    }

    @Test
    public void requestCheckGameOverTestWhenNotGameOver() {
        when(scavHuntState.isGameOver()).thenReturn(false);

        Boolean gameOver = gameController.requestCheckGameOver();

        verify(scavHuntState).isGameOver();
        assertFalse(gameOver);
    }

    @Test
    public void startQuestionActivityTest() {
    }

    @Test
    public void generateZoneRouteTest() {
    }

    @Test
    public void generateQuestionSetTest() {
    }
}
