package com.example.dailyplanner.di

import android.app.Application
import android.content.Context
import com.example.dailyplanner.presentation.edit_screen.FragmentEdit
import com.example.dailyplanner.presentation.main_screen.FragmentMain
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragmentMain: FragmentMain)

    fun inject(fragmentEdit: FragmentEdit)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context
        ): AppComponent
    }
}