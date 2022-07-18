package com.example.dailyplanner.domain.repository

import androidx.lifecycle.LiveData
import com.example.dailyplanner.domain.model.TaskItem
import java.time.LocalDate

interface TaskRepository {

    suspend fun addTask(taskItem: TaskItem)

    suspend fun deleteTask(taskItem: TaskItem)

    suspend fun changeTask(taskItem: TaskItem)

    fun getTaskItemList(date: LocalDate): LiveData<List<TaskItem>>
}