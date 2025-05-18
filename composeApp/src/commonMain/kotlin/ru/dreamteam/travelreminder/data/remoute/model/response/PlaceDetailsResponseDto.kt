package ru.dreamteam.travelreminder.data.remoute.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsResponseDto(
    @SerialName("location")
    val location: LatLngDto
)

@Serializable
data class LatLngDto(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)
