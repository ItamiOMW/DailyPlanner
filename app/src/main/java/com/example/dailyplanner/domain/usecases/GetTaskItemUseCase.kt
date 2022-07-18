package com.example.dailyplanner.domain.usecases

import com.example.dailyplanner.domain.repository.TaskRepository
import java.time.LocalDate

class GetTaskItemUseCase(private val repository: TaskRepository) {

    operator fun invoke(date: LocalDate) = repository.getTaskItemList(date)

}