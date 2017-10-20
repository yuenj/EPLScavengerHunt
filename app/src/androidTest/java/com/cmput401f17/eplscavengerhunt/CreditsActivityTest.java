package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.activity.CreditsActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

public class CreditsActivityTest extends ActivityInstrumentationTestCase2<CreditsActivity> {
    private static final String ERR_MSG = "Wrong Activity";

    private Solo solo;

    public CreditsActivityTest() {
        super(CreditsActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCreditsActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(ERR_MSG, CreditsActivity.class);
        TextView creditsMessage = solo.getCurrentActivity().findViewById(R.id.credits_text_view);
        assertEquals(creditsMessage.getText().toString(), solo.getString(R.string.credits_text));
    }

    public void testReturnButtonShouldGoToTitleActivity() {
        solo.clickOnText(solo.getString(R.string.return_button_text));
        solo.assertCurrentActivity(ERR_MSG, TitleActivity.class);
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
