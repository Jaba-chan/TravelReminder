package ru.dreamteam.travelreminder.data.mapper.params

import ru.dreamteam.travelreminder.data.remoute.model.travel.TimeDto
import ru.dreamteam.travelreminder.domen.model.travel.Time

fun TimeDto.toDomain(): Time = Time(
    hours = hours,
    minutes = minutes
)

fun Time.toDto(): TimeDto = TimeDto(
    hours = hours,
    minutes = minutes
)