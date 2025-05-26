package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.mapper.params.toDomain
import ru.dreamteam.travelreminder.data.mapper.params.toDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Date
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.Route
import ru.dreamteam.travelreminder.domen.model.travel.Time
import ru.dreamteam.travelreminder.domen.model.travel.Travel

fun TravelDto.toEntity(): TravelEntity = TravelEntity(
    id = id,
    date = "date",
    title = title,
    destinationByAddress = "destinationByAddress",
    latitude = 10.0,
    longitude = 10.0,
    arrivalTime = "arrivalTime",
    transportationMode = transportationMode,
    timeBeforeRemind = "timeBeforeRemind"
)

fun TravelDto.toDomain(): Travel = Travel(
    id = id,
    date = date.toDomain(),
    title = title,
    startPlace = startPlace.toDomain(),
    destinationPlace = destinationPlace.toDomain(),
    arrivalTime = arrivalTime.toDomain(),
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind.toDomain(),
    route = route.toDomain()
)

fun Travel.toDto(): TravelDto = TravelDto(
    id = id,
    title = title,
    date = date.toDto(),
    startPlace = startPlace.toDto(),
    destinationPlace = destinationPlace.toDto(),
    arrivalTime = arrivalTime.toDto(),
    transportationMode = transportationMode,
    timeBeforeRemind = timeBeforeRemind.toDto(),
    route = route.toDto()
)

fun TravelEntity.toDomain(): Travel = Travel(
    id = id,
    date = Date(1,2,3),
    title = title,
    startPlace = Place("ddd", "ddd", "ddd", Point(10.0, 10.0)),
    destinationPlace = Place("ddd", "ddd", "ddd", Point(10.0, 10.0)),
    arrivalTime = Time(1,2),
    transportationMode = transportationMode,
    timeBeforeRemind = Time(1,2),
    route = Route(listOf(), distance = 100, duration = "ddd")
)