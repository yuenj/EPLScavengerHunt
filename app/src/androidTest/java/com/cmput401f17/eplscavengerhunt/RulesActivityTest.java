package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.activity.RulesActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

public class RulesActivityTest extends ActivityInstrumentationTestCase2<RulesActivity> {
    private static final String ERR_MSG = "Wrong Activity";

    private Solo solo;

    public RulesActivityTest() {
        super(RulesActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testRulesActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(ERR_MSG, RulesActivity.class);

        TextView rulesMessage1 = solo.getCurrentActivity().findViewById(R.id.rules_text_view_1);
        assertEquals(rulesMessage1.getText().toString(), solo.getString(R.string.rules_welcome_msg_1));
        TextView rulesMessage2 = solo.getCurrentActivity().findViewById(R.id.rules_text_view_2);
        assertEquals(rulesMessage2.getText().toString(), solo.getString(R.string.rules_welcome_msg_2));
        TextView rulesMessage3 = solo.getCurrentActivity().findViewById(R.id.rules_text_view_3);
        assertEquals(rulesMessage3.getText().toString(), solo.getString(R.string.rules_welcome_msg_3));
        TextView rulesMessage4 = solo.getCurrentActivity().findViewById(R.id.rules_text_view_4);
        assertEquals(rulesMessage4.getText().toString(), solo.getString(R.string.rules_welcome_msg_4));
        TextView rulesMessage5 = solo.getCurrentActivity().findViewById(R.id.rules_text_view_5);
        assertEquals(rulesMessage5.getText().toString(), solo.getString(R.string.rules_welcome_msg_5));
    }

    public void testStartButtonShouldGoToDebugActivity() {
        solo.clickOnText(solo.getString(R.string.start_button_text));
        solo.assertCurrentActivity(ERR_MSG, DebugActivity.class);
    }

    public void testReturnButtonShouldGoToTitleActivity() {
        solo.clickOnText(solo.getString(R.string.return_button_text));
        solo.assertCurrentActivity(ERR_MSG, TitleActivity.class);
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
