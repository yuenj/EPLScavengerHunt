package com.cmput401f17.eplscavengerhunt;

import android.app.Activity;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput401f17.eplscavengerhunt.activity.AboutActivity;
import com.cmput401f17.eplscavengerhunt.activity.CreditsActivity;
import com.cmput401f17.eplscavengerhunt.activity.DebugActivity;
import com.cmput401f17.eplscavengerhunt.activity.LocationActivity;
import com.cmput401f17.eplscavengerhunt.activity.QuestionActivity;
import com.cmput401f17.eplscavengerhunt.activity.RulesActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;
import com.robotium.solo.Solo;

import org.junit.runner.RunWith;

import java.util.ArrayList;

import javax.inject.Inject;

public class QuestionActivityTest extends ActivityInstrumentationTestCase2<QuestionActivity> {

    @Inject
    GameController gameController;

    QuestionActivity questionActivity;

    private static final String errMsg = "Wrong Activity";

    private Solo solo;

    public QuestionActivityTest() {
        super(QuestionActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
//        Question question = new Question();
//        ArrayList<Question> questions = new ArrayList<Question>();
//        questions.add(question);
//        scavHuntState.setQuestions(questions);
//        scavHuntState.setCurrentStage(0);

        solo.assertCurrentActivity(errMsg, QuestionActivity.class);
    }
}