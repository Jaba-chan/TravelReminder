package ru.dreamteam.travelreminder.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.dreamteam.travelreminder.domen.model.Travel

interface TravelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(travel: Travel)

    @Query("SELECT * FROM travels WHERE userId = :userID")
    fun getAllAsFlowByUserId(userId: Int): Flow<List<Travel>>

    @Query("SELECT COUNT(*) as count FROM travels")
    suspend fun count(): Int

}