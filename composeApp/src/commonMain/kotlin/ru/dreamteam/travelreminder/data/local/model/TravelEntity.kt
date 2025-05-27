package ru.dreamteam.travelreminder.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travels")
data class TravelEntity(
    @PrimaryKey val id: String,
    val title: String,
    val date: String,
    val startPlaceJson: String,
    val destinationPlaceJson: String,
    val arrivalTimeJson: String,
    val transportationMode: String,
    val timeBeforeRemindJson: String,
    val routeJson: String
)

