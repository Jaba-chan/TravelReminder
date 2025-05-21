package ru.dreamteam.travelreminder.data.local.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.local.model.TravelEntity

private const val DATABASE_VERSION = 2
@Database(entities = [TravelEntity::class], version = DATABASE_VERSION)
abstract  class TravelsDatabase: RoomDatabase() {
    abstract fun travelsDao(): TravelsDao
}
