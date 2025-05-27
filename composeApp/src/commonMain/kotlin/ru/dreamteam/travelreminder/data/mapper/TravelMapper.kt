package ru.dreamteam.travelreminder.data.mapper

import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.mapper.params.toDomain
import ru.dreamteam.travelreminder.data.mapper.params.toDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.DateDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.PlaceDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.RouteSaveDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TimeDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel

fun Travel.toEntity(): TravelEntity = TravelEntity(
    id = id,
    title = title,
    date = Json.encodeToString(date.toDto()),
    startPlaceJson = Json.encodeToString(startPlace.toDto()),
    destinationPlaceJson = Json.encodeToString(destinationPlace.toDto()),
    arrivalTimeJson = Json.encodeToString(arrivalTime.toDto()),
    transportationMode = transportationMode.name,
    timeBeforeRemindJson = Json.encodeToString(timeBeforeRemind.toDto()),
    routeJson = Json.encodeToString(route.toDto())
)

fun TravelEntity.toDomain(): Travel = Travel(
    id = id,
    title = title,
    date = Json.decodeFromString<DateDto>(date).toDomain(),
    startPlace = Json.decodeFromString<PlaceDto>(startPlaceJson).toDomain(),
    destinationPlace = Json.decodeFromString<PlaceDto>(destinationPlaceJson).toDomain(),
    arrivalTime = Json.decodeFromString<TimeDto>(arrivalTimeJson).toDomain(),
    transportationMode = TransportationMode.valueOf(transportationMode),
    timeBeforeRemind = Json.decodeFromString<TimeDto>(timeBeforeRemindJson).toDomain(),
    route = Json.decodeFromString<RouteSaveDto>(routeJson).toDomain()
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
