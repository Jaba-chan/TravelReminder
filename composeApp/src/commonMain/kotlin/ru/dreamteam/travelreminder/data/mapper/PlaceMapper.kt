package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.remoute.model.travel.PlaceDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.Travel

fun PlaceDto.toDomain(): Place = Place(
    placeId = placeId,
    title = title,
    description = description,
    point = point.toDomain()
)

fun Place.toDto(): PlaceDto = PlaceDto(
    placeId = placeId,
    title = title,
    description = description,
    point = point.toDto()
)