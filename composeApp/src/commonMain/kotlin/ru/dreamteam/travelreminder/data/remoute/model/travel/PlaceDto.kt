package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    @SerialName("placeId")
    val placeId: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("point")
    val point: PointDto
)
