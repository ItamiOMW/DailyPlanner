package com.example.dailyplanner.data.repository_impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.dailyplanner.data.database.TaskItemDataBase
import com.example.dailyplanner.data.mapper.TaskItemMapper
import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.repository.TaskRepository
import java.time.LocalDate

class TaskRepositoryImpl(application: Application) : TaskRepository {

    private val mapper = TaskItemMapper()
    private val taskDao = TaskItemDataBase.getInstance(application).taskItemDao()

    override suspend fun addTask(taskItem: TaskItem) {
        taskDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun deleteTask(taskItem: TaskItem) {
        taskDao.deleteTaskItem(taskItem.id)
    }

    override suspend fun changeTask(taskItem: TaskItem) {
        taskDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun getTaskItem(taskItem: TaskItem): TaskItem {
        val dbModel = taskDao.getTaskItem(taskItem.id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getTaskItemList(date: LocalDate): LiveData<List<TaskItem>> =
        Transformations.map(taskDao.getTaskList()) {
            it.filter { it.date == date }.map { mapper.mapDbModelToEntity(it) }
        }
}