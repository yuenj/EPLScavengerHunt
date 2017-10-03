package com.cmput401f17.eplscavengerhunt;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ryan on 2017-10-03.
 */

public class QuestionModelTest {

    @Test
    public void getQuestionIDTest() {
        String questionStrDummy = "Question??";
        int intDummy = 300;
        String solutionStrDummy = "Solution!";

        Question testQuestion = new Question(intDummy,questionStrDummy, solutionStrDummy);

        int returnedQID = testQuestion.getQuestionID();

        assertEquals(intDummy,returnedQID);
    }

    @Test
    public void setQuestionIDTest() {
        int intDummy = 300;
        Question testQuestion = new Question();

        testQuestion.setQuestionID(intDummy);

        assertEquals(testQuestion.getQuestionID(), intDummy);
    }

    @Test
    public void getQuestionTextTest() {
        String questionStrDummy = "Question??";
        String solutionStrDummy = "Solution!";
        int intDummy = 300;
        Question testQuestion = new Question(intDummy, questionStrDummy, solutionStrDummy);

        String returnedText = testQuestion.getQuestionText();

        assertEquals(returnedText, questionStrDummy);
    }

    @Test
    public void setQuestionText() {
        String questionStrDummy = "Question??";
        Question testQuestion = new Question();

        testQuestion.setQuestionText(questionStrDummy);

        assertEquals(testQuestion.getQuestionText(), questionStrDummy);
    }

    @Test
    public void getSolutionTest() {
        String questionStrDummy = "Question??";
        int intDummy = 300;
        String solutionStrDummy = "Solution!";

        Question testQuestion = new Question(intDummy, questionStrDummy, solutionStrDummy);

        String returnedText = testQuestion.getSolution();

        assertEquals(returnedText, solutionStrDummy);
    }

    @Test
    public void setSolutionTest() {
        String solutionStrDummy = "Solution!";
        Question testQuestion = new Question();

        testQuestion.setSolution(solutionStrDummy);

        assertEquals(testQuestion.getSolution(), solutionStrDummy);
    }

    @Test
    public void skipTest()  {
        Question testQuestion = new Question();

        testQuestion.skip();

        assertTrue(testQuestion.isSkipped());
    }

}
