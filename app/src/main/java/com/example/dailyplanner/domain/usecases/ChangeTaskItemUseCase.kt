package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.repository.TaskRepository

class ChangeTaskItemUseCase(private val repository: TaskRepository) {

    suspend operator fun invoke(taskItem: TaskItem) = repository.changeTask(taskItem)

}