package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.activity.AboutActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

public class AboutActivityTest extends ActivityInstrumentationTestCase2<AboutActivity> {
    private static final String ERR_MSG = "Wrong Activity";

    private Solo solo;

    public AboutActivityTest() {
        super(AboutActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAboutActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(ERR_MSG, AboutActivity.class);
        TextView aboutMessage = solo.getCurrentActivity().findViewById(R.id.about_text_view);
        assertEquals(aboutMessage.getText().toString(), solo.getString(R.string.about_text));
    }

    public void testReturnButtonShouldGoToTitleActivity() {
        solo.clickOnText(solo.getString(R.string.return_button_text));
        solo.assertCurrentActivity(ERR_MSG, TitleActivity.class);
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
