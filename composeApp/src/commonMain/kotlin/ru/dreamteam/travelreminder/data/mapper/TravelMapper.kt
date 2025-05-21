package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.Travel

fun TravelDto.toEntity(): TravelEntity = TravelEntity(
    id = id,
    date = date,
    title = title,
    destinationByAddress = destinationByAddress,
    latitude = this.destinationByPoint?.latitude,
    longitude = this.destinationByPoint?.latitude,
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind,
)

fun TravelDto.toDomain(): Travel = Travel(
    id = id,
    date = date,
    title = title,
    destinationByAddress = destinationByAddress,
    destinationByPoint = destinationByPoint,
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind
)

fun TravelEntity.toDomain(): Travel = Travel(
    id = id,
    title = title,
    date = date,
    destinationByAddress = destinationByAddress,
    destinationByPoint = PointDto(latitude, longitude),
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind
)

fun Travel.toDto(): TravelDto = TravelDto(
    id = id,
    title = title,
    date = date,
    destinationByAddress = destinationByAddress,
    destinationByPoint = destinationByPoint,
    arrivalTime = arrivalTime,
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind,
)