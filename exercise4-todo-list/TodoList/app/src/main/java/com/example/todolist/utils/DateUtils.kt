package com.example.todolist.utils

import java.util.Calendar
import java.util.Date

fun createDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day, hour, minute, 0)
    return calendar.time
}