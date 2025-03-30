package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.Serializable

@Serializable
data class Point (
    val latitude: Double?,
    val longitude: Double?
)