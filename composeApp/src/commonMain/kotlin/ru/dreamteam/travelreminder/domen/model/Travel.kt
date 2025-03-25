package ru.dreamteam.travelreminder.domen.model

import kotlinx.serialization.Serializable

@Serializable
data class Travel (
    val id: Int,
    val date: String,
    val destinationByAddress: String?,
    val destinationByPoint: Point?,
    val arrivalTime: String,
    val transportationMode: TransportationMode,
    val timeBeforeRemind: String,
    val userId: Int
)

