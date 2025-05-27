package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteSaveDto(
    @SerialName("poly")
    val poly: List<PointDto>,
    @SerialName("duration")
    val duration: String,
    @SerialName("distance")
    val distance:Int
)