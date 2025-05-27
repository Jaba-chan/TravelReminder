package ru.dreamteam.travelreminder.data.local.room_db

import org.koin.core.module.Module

internal expect fun platformTravelDatabaseModule(
    travelsDatabaseName: String,
    syncActionsDatabaseName: String
): Module