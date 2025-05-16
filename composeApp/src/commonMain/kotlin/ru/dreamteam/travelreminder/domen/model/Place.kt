package ru.dreamteam.travelreminder.domen.model

import ru.dreamteam.travelreminder.domen.model.travel.Point

data class Place(
    val placeId: String,
    val title: String,
    val description: String,
    val point: Point
)
