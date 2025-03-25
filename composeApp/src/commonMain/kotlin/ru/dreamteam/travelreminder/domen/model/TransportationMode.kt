package ru.dreamteam.travelreminder.domen.model

import kotlinx.serialization.Serializable

@Serializable
enum class TransportationMode {
    CAR, BUS, TRAIN, WALK, BIKE
}