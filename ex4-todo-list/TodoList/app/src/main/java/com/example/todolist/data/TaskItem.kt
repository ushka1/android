package com.example.todolist.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class TaskItem(
    var name: String,
    var description: String,
    var createdAt: Date,
    var completed: Boolean,
    var id: UUID = UUID.randomUUID(),
) {
    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(createdAt)
    }
}
