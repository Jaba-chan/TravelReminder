package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.remoute.model.travel.Point
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto

fun TravelEntity.toDomain(): TravelDto = TravelDto(
    id = id,
    date = date,
    destinationByAddress = destinationByAddress,
    destinationByPoint = Point(latitude = latitude, longitude = longitude),
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind,
    userId = 1
)

fun TravelDto.toEntity(): TravelEntity = TravelEntity(
    id = id,
    date = date,
    destinationByAddress = destinationByAddress,
    latitude = this.destinationByPoint?.latitude,
    longitude = this.destinationByPoint?.latitude,
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind,
)
