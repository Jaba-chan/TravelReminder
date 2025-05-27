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
    val date: DateDto,
    @SerialName("destinationPlace")
    val startPlace: PlaceDto,
    @SerialName("startPlace")
    val destinationPlace: PlaceDto,
    @SerialName("arrivalTime")
    val arrivalTime: TimeDto,
    @SerialName("transportationMode")
    val transportationMode: TransportationMode,
    @SerialName("timeBeforeRemind")
    val timeBeforeRemind: TimeDto,
    @SerialName("route")
    val route: RouteSaveDto
)
