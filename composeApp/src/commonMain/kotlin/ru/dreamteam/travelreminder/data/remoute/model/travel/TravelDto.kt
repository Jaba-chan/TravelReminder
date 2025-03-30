package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TravelDto (
    @SerialName("id")
    val id: Long,
    @SerialName("date")
    val date: String,
    @SerialName("destinationByAddress")
    val destinationByAddress: String? = null,
    @SerialName("destinationByPoint")
    val destinationByPoint: Point? = null,
    @SerialName("arrivalTime")
    val arrivalTime: String,
    @SerialName("transportationMode")
    val transportationMode: TransportationMode,
    @SerialName("timeBeforeRemind")
    val timeBeforeRemind: String,
    @SerialName("userId")
    val userId: Int
)

