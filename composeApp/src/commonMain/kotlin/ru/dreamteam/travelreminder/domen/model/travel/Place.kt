package ru.dreamteam.travelreminder.domen.model.travel

data class Place(
    val placeId: String,
    val title: String,
    val description: String,
    val point: Point
)
