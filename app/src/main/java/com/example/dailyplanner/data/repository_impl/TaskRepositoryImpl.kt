package com.example.dailyplanner.data.repository_impl

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.dailyplanner.data.database.TaskItemDao
import com.example.dailyplanner.data.database.TaskItemDataBase
import com.example.dailyplanner.data.mapper.TaskItemMapper
import com.example.dailyplanner.domain.model.TaskItem
import com.example.dailyplanner.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val mapper: TaskItemMapper,
    private val taskDao: TaskItemDao
    ) : TaskRepository {

    override suspend fun addTask(taskItem: TaskItem) {
        taskDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun deleteTask(taskItem: TaskItem) {
        taskDao.deleteTaskItem(taskItem.id)
    }

    override suspend fun changeTask(taskItem: TaskItem) {
        taskDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun getTaskItem(id: Int): TaskItem {
        val dbModel = taskDao.getTaskItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun getTaskItemList(date: String): List<TaskItem> =
        taskDao.getTaskList(date).map { mapper.mapDbModelToEntity(it) }
}