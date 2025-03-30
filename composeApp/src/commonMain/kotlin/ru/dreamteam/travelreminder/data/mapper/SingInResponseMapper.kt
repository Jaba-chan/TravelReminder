package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.SignInResponseDto
import ru.dreamteam.travelreminder.domen.model.SignInResponse

fun SignInResponseDto.toDomain(): SignInResponse = SignInResponse(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId,
    registered = registered
)

fun SignInResponse.toDao(): SignInResponseDto = SignInResponseDto(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId,
    registered = registered
)