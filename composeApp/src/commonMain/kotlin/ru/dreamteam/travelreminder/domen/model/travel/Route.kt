package ru.dreamteam.travelreminder.domen.model.travel

data class Route(
    val poly: List<Point>,
    val duration: String,
    val distance:Int
)