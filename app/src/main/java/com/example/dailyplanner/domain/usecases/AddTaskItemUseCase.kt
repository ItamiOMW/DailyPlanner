package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.repository.TaskRepository

class AddTaskItemUseCase(private val repository: TaskRepository) {

    operator fun invoke(taskItem: TaskItem) = repository.addTask(taskItem)

}