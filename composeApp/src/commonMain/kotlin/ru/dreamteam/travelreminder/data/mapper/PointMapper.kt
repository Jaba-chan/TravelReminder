package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto
import ru.dreamteam.travelreminder.domen.model.travel.Point

fun PointDto.toDomain(): Point = Point(
    latitude = latitude,
    longitude = longitude
)

fun Point.toDto(): PointDto = PointDto(
    latitude = latitude,
    longitude = longitude
)