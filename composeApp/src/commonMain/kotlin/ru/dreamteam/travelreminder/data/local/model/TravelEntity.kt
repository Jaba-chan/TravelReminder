package ru.dreamteam.travelreminder.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TravelEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val destinationByAddress: String?,
    val latitude: Double?,
    val longitude: Double?,
    val arrivalTime: String,
    val transportationMode: String,
    val timeBeforeRemind: String,
    val userId: Int
)

