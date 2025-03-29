package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable

@Serializable
data class Point (
    val latitude: Double?,
    val longitude: Double?
)