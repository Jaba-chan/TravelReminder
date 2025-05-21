package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.Serializable

@Serializable
enum class TransportationModeDto {
    TRAVEL_MODE_UNSPECIFIED,
    DRIVE,
    BICYCLE,
    WALK,
    TWO_WHEELER,
    TRANSIT
}