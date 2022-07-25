package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.repository.TaskRepository

class GetTaskItemUseCase(private val repository: TaskRepository) {

    suspend operator fun invoke(id: Int) = repository.getTaskItem(id)
}