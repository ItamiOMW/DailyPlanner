package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.repository.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor (private val repository: TaskRepository) {

    suspend operator fun invoke(date: String) = repository.getTaskItemList(date)

}