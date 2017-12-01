package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.activity.QuestionActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Tests QuestionActivityTest
 */
public class QuestionActivityTest extends ActivityInstrumentationTestCase2<com.cmput401f17.eplscavengerhunt.activity.QuestionActivity> {
    private static final String ERR_MSG = "Wrong Activity";

    private Solo solo;

    public QuestionActivityTest() {
        super(com.cmput401f17.eplscavengerhunt.activity.QuestionActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAboutActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(ERR_MSG, QuestionActivity.class);

        /* Find skip button as all views/layouts displayed by QuestionActivityTest contain a skip button */
        Button skipButton = solo.getCurrentActivity().findViewById(R.id.question_skip_button);

        /* Views/ layouts displayed by QuestionActivityTest all contain textviews(zone and questionPrompt), but content is different */
        TextView congratsMsgHeader = solo.getCurrentActivity().findViewById(R.id.question_zone_text_view);
        TextView congratsMsgBody = solo.getCurrentActivity().findViewById(R.id.question_prompt_text_view);
    }

    public void testSkipButton(){
        solo.waitForActivity("QuestionActivity");

//        assertTrue(solo.searchText("skip_question"));

//        /* Find skip button as all views/layouts displayed by QuestionActivityTest contain a skip button */
//        Button skipButton = (Button) solo.getView(R.id.question_skip_button);
//        solo.clickOnButton(R.string.skip_question);
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
