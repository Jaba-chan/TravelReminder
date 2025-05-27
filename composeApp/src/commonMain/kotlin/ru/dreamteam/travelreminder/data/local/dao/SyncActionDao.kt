package ru.dreamteam.travelreminder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.dreamteam.travelreminder.data.local.model.SyncActionEntity

@Dao
interface SyncActionDao {
    @Insert
    suspend fun enqueueAction(action: SyncActionEntity)

    @Query("SELECT * FROM sync_actions ORDER BY timestamp ASC")
    suspend fun getPendingActions(): List<SyncActionEntity>

    @Delete
    suspend fun removeAction(action: SyncActionEntity)
}