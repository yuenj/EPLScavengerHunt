package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput401f17.eplscavengerhunt.activity.AboutActivity;
import com.cmput401f17.eplscavengerhunt.activity.CreditsActivity;
import com.cmput401f17.eplscavengerhunt.activity.LocationActivity;
import com.cmput401f17.eplscavengerhunt.activity.RulesActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

public class TitleActivityTest extends ActivityInstrumentationTestCase2<TitleActivity> {
    private static final String errMsg = "Wrong Activity";

    private Solo solo;

    public TitleActivityTest() {
        super(TitleActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testTitleActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(errMsg, TitleActivity.class);
    }

    public void testStartButtonShouldGoToLocationActivity() {
        solo.clickOnText(solo.getString(R.string.start_button_text));
        solo.assertCurrentActivity(errMsg, LocationActivity.class);
    }

    public void testRulesButtonShouldGoToRulesActivity() {
        solo.clickOnText(solo.getString(R.string.rules_button_text));
        solo.assertCurrentActivity(errMsg, RulesActivity.class);
    }

    public void testAboutButtonShouldGoToAboutActivity() {
        solo.clickOnText(solo.getString(R.string.about_button_text));
        solo.assertCurrentActivity(errMsg, AboutActivity.class);
    }

    public void testCreditsButtonShouldGoToCreditsActivity() {
        solo.clickOnText(solo.getString(R.string.credits_button_text));
        solo.assertCurrentActivity(errMsg, CreditsActivity.class);
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
