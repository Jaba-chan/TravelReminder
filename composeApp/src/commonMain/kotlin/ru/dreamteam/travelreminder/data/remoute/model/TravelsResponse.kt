package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable

@Serializable
data class TravelsResponse(
    val travels: Map<String, Travel>
)