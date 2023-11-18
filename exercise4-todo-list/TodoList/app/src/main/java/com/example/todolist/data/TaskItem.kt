package com.example.todolist.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TaskItem(
    val id: String,
    val name: String,
    val description: String,
    val createdAt: Date,
    val completed: Boolean
) {
    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(createdAt)
    }
}
