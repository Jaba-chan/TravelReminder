package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.travel.DateDto
import ru.dreamteam.travelreminder.domen.model.travel.Date

fun DateDto.toDomain(): Date = Date(
    year = year,
    month = month,
    day = day
)

fun Date.toDto(): DateDto = DateDto(
    year = year,
    month = month,
    day = day
)