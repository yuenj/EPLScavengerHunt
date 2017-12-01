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
 * Instrumentation test that tests game completion use cases (UC 6,7), while also
 * completing each question by skipping them and checking if skipping was
 * correctly recorded in player responses(UC5)
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GameCompletionTest {
    private long waitingTime;

    @Rule
    public ActivityTestRule<TitleActivity> mActivityRule = new ActivityTestRule<>(
            TitleActivity.class);

    @Before
    public void init() {
        waitingTime = DateUtils.SECOND_IN_MILLIS;
    }

    // USE CASE 5,6,7
    @Test
    public void GameCompletionTestWithSkippedQuestions() {
        // from title screen click start button
        onView(withId(R.id.title_start_button)).perform(click());

        // skip 5 questions
        for (int i=0;i<5;i++) {
            // Wait for locationactivity to dismiss
            try {
                Thread.sleep(waitingTime*2);
            } catch (Exception e) {
                System.out.print("Sleep error");
            }

            // complete the question by skipping (UC5)
            onView(withId(R.id.question_skip_button)).perform(click());

            // Click next on answer
            onView(withId(R.id.button_question_answer_done)).perform(click());
        }

        // after last questions response submitted, verify that Congratulations-
        // Activity  with congratulations message displayed (UC6)
        onView(withId(R.id.button_congrats_view_summary)).check(matches(isDisplayed()));

        // click button to see SummaryActivity
        onView(withId(R.id.button_congrats_view_summary)).perform(click());

        // verify SummaryActivity displayed
        onView(withId(R.id.button_summary_replay)).check(matches(isDisplayed()));

    }

    @After
    public void after() {
        mActivityRule.finishActivity();
    }

}