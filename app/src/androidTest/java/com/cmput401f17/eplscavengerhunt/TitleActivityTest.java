package com.cmput401f17.eplscavengerhunt;

import android.app.Activity;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput401f17.eplscavengerhunt.activity.AboutActivity;
import com.cmput401f17.eplscavengerhunt.activity.CreditsActivity;
import com.cmput401f17.eplscavengerhunt.activity.DebugActivity;
import com.cmput401f17.eplscavengerhunt.activity.RulesActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

public class TitleActivityTest extends ActivityInstrumentationTestCase2<TitleActivity> {
    private Solo solo;
    private Resources res;

    private static final String errMsg = "Wrong Activity";

    public TitleActivityTest() {
        super(TitleActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        res = getInstrumentation().getTargetContext().getResources();
    }

    public void testStart() throws Exception {
        solo.assertCurrentActivity(errMsg, TitleActivity.class);
    }

    public void testStartButtonShouldGoToDebugActivity() {
        solo.clickOnText(res.getString(R.string.start_button_text));
        solo.assertCurrentActivity(errMsg, DebugActivity.class);
    }

    public void testRulesButtonShouldGoToRulesActivity() {
        solo.clickOnText(res.getString(R.string.rules_button_text));
        solo.assertCurrentActivity(errMsg, RulesActivity.class);
    }

    public void testAboutButtonShouldGoToAboutActivity() {
        solo.clickOnText(res.getString(R.string.about_button_text));
        solo.assertCurrentActivity(errMsg, AboutActivity.class);
    }

    public void testCreditsButtonShouldGoToCreditsActivity() {
        solo.clickOnText(res.getString(R.string.credits_button_text));
        solo.assertCurrentActivity(errMsg, CreditsActivity.class);
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
