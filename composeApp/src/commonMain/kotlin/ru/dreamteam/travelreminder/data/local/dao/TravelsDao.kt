package ru.dreamteam.travelreminder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.dreamteam.travelreminder.data.local.model.TravelEntity

@Dao
interface TravelsDao {
    @Query("SELECT * FROM travels")
    suspend fun getAll(): List<TravelEntity>

    @Query("SELECT * FROM travels WHERE id = :id")
    suspend fun getById(id: String): TravelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(travel: TravelEntity)

    @Update
    suspend fun update(travel: TravelEntity)

    @Delete
    suspend fun delete(travel: TravelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun fill(travels: List<TravelEntity>)

    @Query("DELETE FROM travels")
    suspend fun clearTable()
    
}