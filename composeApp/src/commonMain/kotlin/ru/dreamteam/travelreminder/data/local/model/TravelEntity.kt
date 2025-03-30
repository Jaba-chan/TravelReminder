package ru.dreamteam.travelreminder.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.dreamteam.travelreminder.data.mapper.TransportationModeConverter
import ru.dreamteam.travelreminder.data.remoute.model.travel.TransportationMode

@Entity(tableName = "travels")
@TypeConverters(TransportationModeConverter::class)
data class TravelEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val destinationByAddress: String?,
    val latitude: Double?,
    val longitude: Double?,
    val arrivalTime: String,
    val transportationMode: TransportationMode,
    val timeBeforeRemind: String,
)

