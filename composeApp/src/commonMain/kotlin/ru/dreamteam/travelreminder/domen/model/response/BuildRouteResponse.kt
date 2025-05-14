package ru.dreamteam.travelreminder.domen.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    val routes: List<Route>
)

@Serializable
data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: Polyline
)

@Serializable
data class Polyline(
    @SerialName("encodedPolyline")
    val encodedPolyline: String
)