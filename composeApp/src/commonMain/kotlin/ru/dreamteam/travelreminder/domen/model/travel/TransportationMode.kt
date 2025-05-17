package ru.dreamteam.travelreminder.domen.model.travel

import kotlinx.serialization.Serializable

@Serializable
enum class TransportationMode {
    DRIVE,
    BICYCLE,
    WALK,
    TWO_WHEELER,
    TRANSIT
}