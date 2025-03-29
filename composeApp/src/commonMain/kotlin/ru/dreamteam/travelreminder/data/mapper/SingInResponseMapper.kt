package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.SignInResponseDao
import ru.dreamteam.travelreminder.domen.model.SignInResponse

fun SignInResponseDao.toDomain(): SignInResponse = SignInResponse(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId,
    registered = registered
)

fun SignInResponse.toDao(): SignInResponseDao = SignInResponseDao(
    idToken = idToken,
    email = email,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    localId = localId,
    registered = registered
)