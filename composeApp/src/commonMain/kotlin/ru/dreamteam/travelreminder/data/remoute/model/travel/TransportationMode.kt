package ru.dreamteam.travelreminder.data.remoute.model.travel

import kotlinx.serialization.Serializable

@Serializable
enum class TransportationMode {
    CAR, BUS, TRAIN, WALK, BIKE
}