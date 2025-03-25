package ru.dreamteam.travelreminder.domen.model

import kotlinx.serialization.Serializable

@Serializable
data class Point (
    val latitude: Double,
    val longitude: Double
)