package com.cmput401f17.eplscavengerhunt;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.activity.CongratulationsActivity;
import com.cmput401f17.eplscavengerhunt.activity.SummaryActivity;
import com.robotium.solo.Solo;

public class CongratulationsActivityTest extends
        ActivityInstrumentationTestCase2<CongratulationsActivity> {
    private static final String ERR_MSG = "Wrong Activity";

    private Solo solo;

    public CongratulationsActivityTest() {
        super(CongratulationsActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCongratulationsActivityIsProperlyDisplayed() {
        solo.assertCurrentActivity(ERR_MSG, CongratulationsActivity.class);
        TextView congratsMsgHeader = solo.getCurrentActivity().findViewById(R.id.TV_congrats_msg_header);
        assertEquals(congratsMsgHeader.getText().toString(), solo.getString(R.string.congrats_msg_header));
        TextView congratsMsgBody = solo.getCurrentActivity().findViewById(R.id.TV_congrats_msg_body);
        assertEquals(congratsMsgBody.getText().toString(), solo.getString(R.string.congrats_msg_body));
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
