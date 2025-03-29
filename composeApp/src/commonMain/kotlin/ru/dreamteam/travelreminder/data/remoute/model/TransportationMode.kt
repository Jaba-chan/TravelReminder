package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable

@Serializable
enum class TransportationMode {
    CAR, BUS, TRAIN, WALK, BIKE
}