package com.example.todolist


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todolist.ui.TaskAdapter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSwipeToDeleteTask() {
        var initialTaskCount = 0
        activityRule.scenario.onActivity { activity ->
            initialTaskCount = activity.getTaskCount()
        }

        for (i in 0 until initialTaskCount) {
            onView(withId(R.id.recycler_view))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<TaskAdapter.TaskViewHolder>(
                        0,
                        swipeRight()
                    )
                )
            Thread.sleep(500)

            var currentTaskCount = 0
            activityRule.scenario.onActivity { activity ->
                currentTaskCount = activity.getTaskCount()
            }

            assertEquals(initialTaskCount - (i + 1), currentTaskCount)
        }

        var finalTaskCount = 0
        activityRule.scenario.onActivity { activity ->
            finalTaskCount = activity.getTaskCount()
        }

        assertEquals(0, finalTaskCount)
    }
}
