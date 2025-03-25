package ru.dreamteam.travelreminder.data.local.room_db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.domen.model.Travel

@Database(entities = [Travel::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract  class TravelsDatabase: RoomDatabase() {
    abstract fun travelsDao(): TravelsDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<TravelsDatabase> {
    override fun initialize(): TravelsDatabase
}
internal const val dbFileName = "travels.db"