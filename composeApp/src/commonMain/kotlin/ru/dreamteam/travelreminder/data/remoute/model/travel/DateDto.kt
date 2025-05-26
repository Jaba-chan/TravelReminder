package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    @SerialName("year")
    val year: Int,
    @SerialName("month")
    val month: Int,
    @SerialName("day")
    val day: Int
)