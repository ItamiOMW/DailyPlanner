package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskItemUseCase @Inject constructor (private val repository: TaskRepository) {

    suspend operator fun invoke(taskItem: TaskItem) = repository.addTask(taskItem)

}