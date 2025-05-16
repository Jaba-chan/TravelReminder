package ru.dreamteam.travelreminder.data.remoute.model.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearbySearchResponseDto(
    val places: List<PlaceDto> = emptyList()
)

@Serializable
data class PlaceDto(
    @SerialName("name")
    val placeId: String,
    @SerialName("displayName")
    val displayName: LocalizedTextDto? = null,
    @SerialName("location")
    val location: LatLngDto,
    @SerialName("formattedAddress")
    val formattedAddress: String? = null
)
