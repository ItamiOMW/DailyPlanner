package com.example.dailyplanner.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskItemDbModel::class], exportSchema = false, version = 3)
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
                ).fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }
}