package com.example.dailyplanner.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate

@Entity(tableName = "task_items")
data class TaskItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val name: String,
    val timeFrom: String,
    val timeTo: String,
    val description: String,
    val isDone: Boolean
) {

}