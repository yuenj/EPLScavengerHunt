package com.cmput401f17.eplscavengerhunt;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Instrumentation test that tests for zone and question completion use cases (UC 2.1,2.2,3)
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ZoneAndQuestionDisplayTest {

    private String mStringToBetyped;
    private long waitingTime;

    @Rule
    public ActivityTestRule<TitleActivity> mActivityRule = new ActivityTestRule<>(
            TitleActivity.class);

    @Before
    public void initValidString() {
        waitingTime = DateUtils.SECOND_IN_MILLIS;
    }

    // USE CASE 2.1, 2.2, 3
    @Test
    public void ZoneAndQuestionDisplayTest() {
        // From title screen click start button
        onView(withId(R.id.title_start_button)).perform(click());

        // check that location of first zone is given (UC 2.2)
        onView(withId(R.id.location_next_zone_text_view)).check(matches(isDisplayed()));

        // Wait for locationactivity to dismiss
        try {
            Thread.sleep(waitingTime*2);
        } catch (Exception e) {
            System.out.print("Sleep error");
        }

        // confirm question displayed after zone arrival (UC 3)
        onView(withId(R.id.question_skip_button)).check(matches(isDisplayed()));

        // complete the question by skipping
        onView(withId(R.id.question_skip_button)).perform(click());

        // Click next on answer
        onView(withId(R.id.button_question_answer_done)).perform(click());

        // Check next zone location is displayed (UC 2.1)
        onView(withId(R.id.location_next_zone_text_view)).check(matches(isDisplayed()));

    }

    @After
    public void after() {
        mActivityRule.finishActivity();
    }

}