package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskItemUseCase @Inject constructor (private val repository: TaskRepository) {

    suspend operator fun invoke(id: Int) = repository.getTaskItem(id)
}