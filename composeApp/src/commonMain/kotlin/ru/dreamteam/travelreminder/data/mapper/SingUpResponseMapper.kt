package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.SignUpResponseDto
import ru.dreamteam.travelreminder.domen.model.SignUpResponse

fun SignUpResponseDto.toDomain(): SignUpResponse = SignUpResponse(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId
)

fun SignUpResponse.toDao(): SignUpResponseDto = SignUpResponseDto(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId
)