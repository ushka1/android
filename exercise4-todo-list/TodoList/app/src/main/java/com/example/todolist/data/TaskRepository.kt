package com.example.todolist.data

import com.example.todolist.utils.createDate

class TaskRepository {

    private val tasks = mutableListOf<TaskItem>(
        TaskItem(
            id = "1",
            name = "Prepare Client Demo",
            description = "Prepare a client demo for the upcoming project meeting.",
            createdAt = createDate(2023, 11, 18, 10, 30),
            completed = false
        ),
        TaskItem(
            id = "2",
            name = "Code Review Session",
            description = "Conduct a code review session for the latest feature implementation.",
            createdAt = createDate(2023, 11, 16, 14, 45),
            completed = false
        ),
        TaskItem(
            id = "3",
            name = "Optimize Database Queries",
            description = "Optimize database queries for improved application performance.",
            createdAt = createDate(2023, 11, 14, 9, 0),
            completed = false
        ),
        TaskItem(
            id = "4",
            name = "Coordinate Sprint Planning",
            description = "Coordinate sprint planning meeting with the development team.",
            createdAt = createDate(2023, 11, 17, 16, 20),
            completed = false
        ),
        TaskItem(
            id = "5",
            name = "Read Tech Articles",
            description = "Read latest tech articles and stay updated with industry trends.",
            createdAt = createDate(2023, 11, 15, 11, 0),
            completed = false
        ),
        TaskItem(
            id = "6",
            name = "Refactor Legacy Code",
            description = "Initiate refactoring of legacy code to enhance code quality.",
            createdAt = createDate(2023, 11, 13, 13, 15),
            completed = false
        ),
        TaskItem(
            id = "7",
            name = "Prepare Presentation for Team Meeting",
            description = "Prepare a presentation for the upcoming team meeting.",
            createdAt = createDate(2023, 11, 21, 9, 30),
            completed = false
        ),
        TaskItem(
            id = "8",
            name = "Review Project Timeline",
            description = "Review and update the project timeline based on recent changes.",
            createdAt = createDate(2023, 11, 22, 14, 0),
            completed = false
        )
    )


    fun getAllTasks(): List<TaskItem> {
        return tasks
    }

    fun getTaskById(id: String): TaskItem? {
        return tasks.find { it.id == id }
    }

    fun addTask(task: TaskItem) {
        tasks.add(task)
    }

    fun removeTask(task: TaskItem) {
        tasks.remove(task)
    }

}