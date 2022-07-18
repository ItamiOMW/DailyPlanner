package com.example.dailyplanner.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "task_items")
data class TaskItemDbModel(
    @PrimaryKey
    val id: Int,
    val date: LocalDate,
    val name: String,
    val timeFrom: String,
    val timeTo: String,
    val description: String,
    val isDone: Boolean
) {

}