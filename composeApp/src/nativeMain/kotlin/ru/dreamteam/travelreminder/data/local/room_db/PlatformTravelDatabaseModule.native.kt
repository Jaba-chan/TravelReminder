package ru.dreamteam.travelreminder.data.local.room_db

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

internal actual fun platformTravelDatabaseModule(fileName: String): Module =
    module(createdAtStart = true) {
        single<TravelsDatabase> { getDatabase(fileName) }
    }

private fun getDatabase(fileName: String): TravelsDatabase =
    Room.databaseBuilder<TravelsDatabase>(
        name = NSHomeDirectory() + "/$fileName",
    ).setQueryCoroutineContext(Dispatchers.IO).build()