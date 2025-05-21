package ru.dreamteam.travelreminder.domen.model.travel

import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto

data class Travel(
    val id: String,
    val title: String,
    val date: String,
    val destinationByAddress: String? = null,
    val destinationByPoint: PointDto? = null,
    val arrivalTime: String,
    val transportationMode: TransportationMode,
    val timeBeforeRemind: String,
)