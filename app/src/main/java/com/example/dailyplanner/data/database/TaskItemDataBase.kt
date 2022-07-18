package com.example.dailyplanner.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItemDbModel::class], exportSchema = false, version = 1)
abstract class TaskItemDataBase : RoomDatabase() {

    abstract fun taskItemDao(): TaskItemDao

    companion object {

        private const val DB_NAME = "task_item.db"
        private val LOCKER = Any()
        private var db: TaskItemDataBase? = null

        fun getInstance(application: Application): TaskItemDataBase {
            db?.let {
                return it
            }
            synchronized(LOCKER) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    application,
                    TaskItemDataBase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
}