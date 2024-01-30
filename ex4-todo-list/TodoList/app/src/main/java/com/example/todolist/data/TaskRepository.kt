package com.example.todolist.data

import java.util.UUID

class TaskRepository(private val datasource: TaskDatasource) {

    fun getAllTasks(): List<TaskItem> {
        return datasource.getAllTasks()
    }

    fun getTaskById(taskId: UUID): TaskItem? {
        return datasource.getTaskById(taskId)
    }

    fun addTask(name: String, description: String): Boolean {
        return datasource.addTask(name, description)
    }

    fun removeTask(taskId: UUID): Boolean {
        return datasource.removeTask(taskId)
    }

}