package com.example.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCalculate() {
        // Write 23.5
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("23.5")))

        // Write +
        onView(withId(R.id.buttonAdd)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("23.5 +")))

        // Write 26.5
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.button6)).perform(click())
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("23.5 + 26.5")))

        // Write =
        onView(withId(R.id.buttonCalculate)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("50.0")))
    }
}