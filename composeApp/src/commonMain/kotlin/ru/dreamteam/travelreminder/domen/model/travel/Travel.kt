package ru.dreamteam.travelreminder.domen.model.travel

data class Travel(
    val id: String,
    val title: String,
    val date: Date,
    val destinationPlace: Place,
    val arrivalTime: Time,
    val transportationMode: TransportationMode,
    val timeBeforeRemind: Time,
)