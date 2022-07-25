package com.example.dailyplanner.di

import androidx.lifecycle.ViewModel
import com.example.dailyplanner.presentation.edit_screen.EditViewModel
import com.example.dailyplanner.presentation.main_screen.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModuleKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModuleKey(EditViewModel::class)
    fun bindEditViewModel(viewModel: EditViewModel): ViewModel
}