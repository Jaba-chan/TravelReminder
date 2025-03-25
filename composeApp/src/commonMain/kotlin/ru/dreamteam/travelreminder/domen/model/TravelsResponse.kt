package ru.dreamteam.travelreminder.domen.model

import kotlinx.serialization.Serializable

@Serializable
data class TravelsResponse(
    val travels: Map<String, Travel>
)