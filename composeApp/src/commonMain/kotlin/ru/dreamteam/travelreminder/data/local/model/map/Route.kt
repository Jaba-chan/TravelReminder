package ru.dreamteam.travelreminder.data.local.model.map

import ru.dreamteam.travelreminder.domen.model.travel.Point

data class Route(
    val poly: List<Point>,
    val duration: String,
    val distance:Int
)