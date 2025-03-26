package ru.dreamteam.travelreminder.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.domen.model.Travel

interface TravelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(travel: Travel)

    @Query("DELETE FROM travels WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM travels")
    fun getAllUserId(): List<TravelEntity>

    @Update
    suspend fun edit(item: TravelEntity)

}