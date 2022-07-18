package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.repository.TaskRepository

class GetTaskItemUseCase(private val repository: TaskRepository) {

    operator fun invoke(taskItemId: Int) = repository.getTaskItem(taskItemId)

}