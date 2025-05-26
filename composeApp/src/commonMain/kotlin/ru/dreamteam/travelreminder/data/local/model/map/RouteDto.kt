package ru.dreamteam.travelreminder.data.local.model.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto

@Serializable
data class RouteSaveDto(
    @SerialName("poly")
    val poly: List<PointDto>,
    @SerialName("duration")
    val duration: String,
    @SerialName("distance")
    val distance:Int
)