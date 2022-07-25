package com.example.dailyplanner.di

import android.app.Application
import com.example.dailyplanner.data.database.TaskItemDao
import com.example.dailyplanner.data.database.TaskItemDataBase
import com.example.dailyplanner.data.repository_impl.TaskRepositoryImpl
import com.example.dailyplanner.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    companion object {

        @Provides
        @AppScope
        fun provideTaskDao(
            application: Application
        ): TaskItemDao {
            return TaskItemDataBase.getInstance(application).taskItemDao()
        }
    }
}