package ru.dreamteam.travelreminder.data.local.room_db

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformTravelDatabaseModule(
    travelsDatabaseName: String,
    syncActionsDatabaseName: String
    ): Module =
    module(createdAtStart = true) {
        single<TravelsDatabase> { getTravelsDatabase(get(), travelsDatabaseName) }
        single<SyncActionsDatabase> { getSyncActionsDatabase(get(), syncActionsDatabaseName) }
    }

private fun getTravelsDatabase(context: Context, fileName: String): TravelsDatabase =
    Room.databaseBuilder<TravelsDatabase>(
        context = context.applicationContext,
        name = context.applicationContext.getDatabasePath(fileName).absolutePath
    )
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

private fun getSyncActionsDatabase(context: Context, fileName: String): SyncActionsDatabase =
    Room.databaseBuilder<SyncActionsDatabase>(
        context = context.applicationContext,
        name = context.applicationContext.getDatabasePath(fileName).absolutePath
    )
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
