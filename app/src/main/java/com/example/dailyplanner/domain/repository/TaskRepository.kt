package com.example.dailyplanner.domain.repository

import com.example.dailyplanner.domain.model.TaskItem

interface TaskRepository {

    suspend fun addTask(taskItem: TaskItem)

    suspend fun deleteTask(taskItem: TaskItem)

    suspend fun changeTask(taskItem: TaskItem)

    fun getTaskItem(taskItemId: Int): TaskItem
}