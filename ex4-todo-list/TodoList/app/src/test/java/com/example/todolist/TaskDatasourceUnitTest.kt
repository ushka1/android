package com.example.todolist

import com.example.todolist.data.TaskDatasource
import com.example.todolist.data.TaskItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

class TaskDatasourceUnitTest {
    private lateinit var initialTasks: MutableList<TaskItem>
    private lateinit var taskDatasource: TaskDatasource

    @Before
    fun setup() {
        val task1 = TaskItem("Test1", "Test Description1", Date(), false)
        val task2 = TaskItem("Test2", "Test Description2", Date(), true)
        initialTasks = mutableListOf(task1, task2)

        taskDatasource = TaskDatasource()
        taskDatasource.setTasks(initialTasks)
    }

    @Test
    fun `getAllTasks returns all tasks`() {
        val tasks = taskDatasource.getAllTasks()

        assertEquals(initialTasks.size, tasks.size)
        assertEquals(initialTasks, tasks)
    }

    @Test
    fun `getTaskById returns correct task`() {
        val taskId = initialTasks[0].id
        val returnedTask = taskDatasource.getTaskById(taskId)

        assertEquals(initialTasks[0], returnedTask)
    }

    @Test
    fun `addTask adds task to list`() {
        val task = TaskItem("Test3", "Test Description3", Date(), false)
        val isAdded = taskDatasource.addTask(task.name, task.description)

        assertTrue(isAdded)
        assertEquals(initialTasks.size + 1, taskDatasource.getAllTasks().size)
    }

    @Test
    fun `removeTask removes task from list`() {
        val taskId = initialTasks[0].id
        val isRemoved = taskDatasource.removeTask(taskId)

        assertTrue(isRemoved)
        assertEquals(initialTasks.size - 1, taskDatasource.getAllTasks().size)
    }

}