package com.example.dailyplanner.data.mapper

import com.example.dailyplanner.data.database.TaskItemDbModel
import com.example.dailyplanner.domain.model.TaskItem
import javax.inject.Inject

class TaskItemMapper @Inject constructor() {

    fun mapDbModelToEntity(model: TaskItemDbModel) = TaskItem(
        id = model.id,
        date = model.date,
        name = model.name,
        timeFrom = model.timeFrom,
        timeTo = model.timeTo,
        description = model.description,
        isDone = model.isDone,
    )

    fun mapEntityToDbModel(model: TaskItem) = TaskItemDbModel(
        id = model.id,
        date = model.date,
        name = model.name,
        timeFrom = model.timeFrom,
        timeTo = model.timeTo,
        description = model.description,
        isDone = model.isDone,
    )
}