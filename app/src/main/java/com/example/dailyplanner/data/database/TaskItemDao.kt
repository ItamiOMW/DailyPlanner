package com.example.dailyplanner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(taskItemDbModel: TaskItemDbModel)

    @Query("DELETE FROM task_items WHERE id=:taskItemId")
    suspend fun deleteTaskItem(taskItemId: Int)

    @Query("SELECT * FROM task_items WHERE id=:taskItemId")
    suspend fun getTaskItem(taskItemId: Int): TaskItemDbModel

    @Query("SELECT * FROM task_items WHERE date=:date")
    suspend fun getTaskList(date: String): List<TaskItemDbModel>
}