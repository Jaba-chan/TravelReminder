package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode

@Serializable
data class TravelDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: String,
    @SerialName("destinationByAddress")
    val destinationByAddress: String? = null,
    @SerialName("destinationByPoint")
    val destinationByPoint: PointDto? = null,
    @SerialName("arrivalTime")
    val arrivalTime: String,
    @SerialName("transportationMode")
    val transportationMode: TransportationMode,
    @SerialName("timeBeforeRemind")
    val timeBeforeRemind: String,
)
