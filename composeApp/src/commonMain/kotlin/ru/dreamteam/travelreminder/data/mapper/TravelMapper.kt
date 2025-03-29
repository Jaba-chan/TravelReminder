package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.remoute.model.Point
import ru.dreamteam.travelreminder.data.remoute.model.Travel

fun TravelEntity.toDomain(): Travel = Travel(
    id = id,
    date = date,
    destinationByAddress = destinationByAddress,
    destinationByPoint = Point(latitude = latitude, longitude = longitude),
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind,
    userId = 1
)

fun Travel.toEntity(): TravelEntity = TravelEntity(
    id = id,
    date = date,
    destinationByAddress = destinationByAddress,
    latitude = this.destinationByPoint?.latitude,
    longitude = this.destinationByPoint?.latitude,
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind,
)
