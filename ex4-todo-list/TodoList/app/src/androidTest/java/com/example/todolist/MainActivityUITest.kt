package com.example.todolist


import android.view.View
import android.widget.CheckBox
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todolist.data.TaskItem
import com.example.todolist.ui.TaskAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
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
    fun testTaskRendering() {
        var taskList = listOf<TaskItem>()
        activityRule.scenario.onActivity { activity ->
            taskList = activity.getTaskList()
        }

        for (i in taskList.indices) {
            onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition<TaskAdapter.TaskViewHolder>(i))

            val task = taskList[i]

            onView(withRecyclerView(R.id.recycler_view).atPositionOnView(i, R.id.task_name))
                .check(matches(withText(task.name)))

            onView(withRecyclerView(R.id.recycler_view).atPositionOnView(i, R.id.task_description))
                .check(matches(withText(task.description)))

            onView(withRecyclerView(R.id.recycler_view).atPositionOnView(i, R.id.task_date))
                .check(matches(withText(task.getFormattedDate())))

            onView(withRecyclerView(R.id.recycler_view).atPositionOnView(i, R.id.task_completed))
                .check(matches(isChecked(task.completed)))
        }
    }

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

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun isChecked(isChecked: Boolean): Matcher<View> {
        return object : BoundedMatcher<View, CheckBox>(CheckBox::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has isChecked: $isChecked")
            }

            override fun matchesSafely(checkBox: CheckBox): Boolean {
                return checkBox.isChecked == isChecked
            }
        }
    }

}
