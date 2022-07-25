package com.example.dailyplanner.di

import android.app.Application

class DailyMainApp: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this, this)
    }
}