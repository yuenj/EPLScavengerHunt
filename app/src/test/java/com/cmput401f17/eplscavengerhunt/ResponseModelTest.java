package com.cmput401f17.eplscavengerhunt;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ryan on 2017-10-03.
 */

public class ResponseModelTest {

    @Test
    public void getResponseTest() {
        String strDummy = "Response!!";
        Response testResponse = new Response(strDummy);

        String returnedStr = testResponse.getResponseStr();

        assertEquals(strDummy, returnedStr);
    }


    @Test
    public void setResponseTest() {
        String strDummy = "Response!!";
        Response testResponse = new Response();

        testResponse.setResponseStr(strDummy);

        assertEquals(strDummy, testResponse.getResponseStr());
    }

    @Test
    public void isCorrectTestOnCorrect() {
        Response testResponse = new Response();

        testResponse.markCorrect();

        assertTrue(testResponse.isCorrect());
    }

    @Test
    public void isCorrectTestOnIncorrect() {
        Response testResponse = new Response();

        testResponse.markIncorrect();

        assertFalse(testResponse.isCorrect());
    }

    @Test
    public void markCorrectTest() {
        Response testResponse = new Response();

        testResponse.markCorrect();

        assertTrue(testResponse.isCorrect());
    }

    @Test
    public void markIncorrectTest() {
        Response testResponse = new Response();

        testResponse.markIncorrect();

        assertFalse(testResponse.isCorrect());
    }
}
