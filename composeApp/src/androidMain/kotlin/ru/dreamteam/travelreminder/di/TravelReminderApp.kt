package ru.dreamteam.travelreminder.di

import android.app.Application
import org.koin.android.ext.koin.androidContext

class TravelReminderApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@TravelReminderApp)
        }
    }
}