package ru.dreamteam.travelreminder.data.local.room_db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformTravelDatabaseModule(fileName: String): Module =
    module(createdAtStart = true) {
        single<TravelsDatabase> { getDatabase(get(), fileName) }
    }

private fun getDatabase(context: Context, fileName: String): TravelsDatabase =
    Room.databaseBuilder<TravelsDatabase>(
        context = context.applicationContext,
        name = context.applicationContext.getDatabasePath(fileName).absolutePath
    ).setQueryCoroutineContext(Dispatchers.IO).build()
