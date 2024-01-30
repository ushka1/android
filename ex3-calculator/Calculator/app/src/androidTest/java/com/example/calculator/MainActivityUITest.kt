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
    fun testAddition() {
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

    @Test
    fun testSubtraction() {
        // Write 15.7
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.button7)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("15.7")))

        // Write -
        onView(withId(R.id.buttonSubtract)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("15.7 -")))

        // Write 8.4
        onView(withId(R.id.button8)).perform(click())
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.button4)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("15.7 - 8.4")))

        // Write =
        onView(withId(R.id.buttonCalculate)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("7.3")))
    }

    @Test
    fun testMultiplication() {
        // Write 12
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("12")))

        // Write *
        onView(withId(R.id.buttonMultiply)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("12 *")))

        // Write 5
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("12 * 5")))

        // Write =
        onView(withId(R.id.buttonCalculate)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("60")))
    }

    @Test
    fun testDivision() {
        // Write 20
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.button0)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("20")))

        // Write /
        onView(withId(R.id.buttonDivide)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("20 /")))

        // Write 4
        onView(withId(R.id.button4)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("20 / 4")))

        // Write =
        onView(withId(R.id.buttonCalculate)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("5")))
    }

    @Test
    fun testModulo() {
        // Write 17
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button7)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("17")))

        // Write %
        onView(withId(R.id.buttonModulo)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("17 %")))

        // Write 3
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("17 % 3")))

        // Write =
        onView(withId(R.id.buttonCalculate)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("2")))
    }

    @Test
    fun testChangeSign() {
        // Write 9
        onView(withId(R.id.button9)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("9")))

        // Write +/-
        onView(withId(R.id.buttonChangeSign)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("-9")))
    }

    @Test
    fun testPower() {
        // Write 7
        onView(withId(R.id.button7)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("7")))

        // Write x^2
        onView(withId(R.id.buttonPower)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("49")))
    }

    @Test
    fun testLogarithm() {
        // Write 100
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button0)).perform(click())
        onView(withId(R.id.button0)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("100")))

        // Write ln
        onView(withId(R.id.buttonLogarithm)).perform(click())
        onView(withId(R.id.result)).check(matches(withText("4.605170185988092")))
    }
    
}