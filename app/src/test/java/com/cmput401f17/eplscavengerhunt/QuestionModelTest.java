package com.cmput401f17.eplscavengerhunt;
import com.cmput401f17.eplscavengerhunt.model.Question;

import org.junit.Test;
import static org.junit.Assert.*;


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
    public void getQuestionPromptTest() {
        String questionStrDummy = "Question??";
        String solutionStrDummy = "Solution!";
        int intDummy = 300;
        Question testQuestion = new Question(intDummy, questionStrDummy, solutionStrDummy);

        String returnedText = testQuestion.getQuestionPrompt();

        assertEquals(returnedText, questionStrDummy);
    }

    @Test
    public void setQuestionPrompt() {
        String questionStrDummy = "Question??";
        Question testQuestion = new Question();

        testQuestion.setQuestionText(questionStrDummy);

        assertEquals(testQuestion.getQuestionPrompt(), questionStrDummy);
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
