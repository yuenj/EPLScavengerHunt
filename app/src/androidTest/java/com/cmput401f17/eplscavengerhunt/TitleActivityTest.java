package com.cmput401f17.eplscavengerhunt;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.DateUtils;

import com.cmput401f17.eplscavengerhunt.activity.AboutActivity;
import com.cmput401f17.eplscavengerhunt.activity.CreditsActivity;
import com.cmput401f17.eplscavengerhunt.activity.LocationActivity;
import com.cmput401f17.eplscavengerhunt.activity.RulesActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.robotium.solo.Solo;

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
 * Instrumentation test that tests for button UI functionality, along with
 * game information-related use cases (UC 1.x)
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TitleActivityTest {
    private long waitingTime;

    @Rule
    public ActivityTestRule<TitleActivity> mActivityRule = new ActivityTestRule<>(
            TitleActivity.class);
    @Before
    public void initValidString() {
        waitingTime = DateUtils.SECOND_IN_MILLIS;
    }

    // UC 1.1
    @Test
    public void testRulesButtonShouldGoToRulesActivity() {
        onView(withId(R.id.title_rules_button)).perform(click());
        onView(withId(R.id.rules_text_view_1)).check(matches(isDisplayed()));
    }

    // UC 1.2
    @Test
    public void testAboutButtonShouldGoToAboutActivity() {
        onView(withId(R.id.title_about_button)).perform(click());
        onView(withId(R.id.about_return_button)).check(matches(isDisplayed()));
    }

    // UC 1.3
    @Test
    public void testCreditsButtonShouldGoToCreditsActivity() {
        onView(withId(R.id.title_credits_button)).perform(click());
        onView(withId(R.id.credits_text_view_2)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        mActivityRule.finishActivity();
    }
}
