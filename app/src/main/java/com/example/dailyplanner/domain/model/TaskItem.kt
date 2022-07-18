package com.example.dailyplanner.domain.model

import java.time.LocalDate

data class TaskItem(
    val date: LocalDate,
    val name: String,
    val timeFrom: String,
    val timeTo: String,
    val description: String,
    val isDone: Boolean
) {
}