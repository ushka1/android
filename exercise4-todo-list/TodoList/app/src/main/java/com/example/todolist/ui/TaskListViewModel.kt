package com.example.todolist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.TaskItem
import com.example.todolist.data.TaskRepository
import java.util.UUID

class TaskListViewModel : ViewModel() {

    private val taskList = MutableLiveData<List<TaskItem>>()
    private val taskRepository = TaskRepository()

    init {
        taskList.value = taskRepository.getAllTasks()
    }

    fun addTask(task: TaskItem) {
        taskRepository.addTask(task)
        taskList.postValue(taskRepository.getAllTasks())
    }

    fun updateTask(id: UUID, name: String, description: String) {
        val list = taskList.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.description = description
        taskList.postValue(list!!)
    }

    fun setCompleted(task: TaskItem) {
        val list = taskList.value
        val task = list!!.find { it.id == task.id }!!
        task.completed = !task.completed
        taskList.postValue(list!!)
    }

}