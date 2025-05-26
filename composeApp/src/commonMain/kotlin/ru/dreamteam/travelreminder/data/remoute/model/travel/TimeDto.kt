package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeDto(
    @SerialName("hours")
    val hours: Int,
    @SerialName("minutes")
    val minutes: Int
)