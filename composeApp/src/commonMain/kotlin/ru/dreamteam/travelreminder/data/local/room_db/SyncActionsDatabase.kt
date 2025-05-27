package ru.dreamteam.travelreminder.data.local.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dreamteam.travelreminder.data.local.dao.SyncActionDao
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.local.model.SyncActionEntity
import ru.dreamteam.travelreminder.data.local.model.TravelEntity

private const val DATABASE_VERSION = 11
@Database(entities = [SyncActionEntity::class], version = DATABASE_VERSION)
abstract class SyncActionsDatabase: RoomDatabase() {
    abstract fun syncDao(): SyncActionDao
}