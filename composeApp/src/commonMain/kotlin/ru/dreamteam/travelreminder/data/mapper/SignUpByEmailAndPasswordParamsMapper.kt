package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.SignUpByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.SignUpByEmailAndPasswordParams

fun SignUpByEmailAndPasswordParams.toRequest(returnSecureToken: Boolean): SignUpByEmailAndPasswordRequestParams = SignUpByEmailAndPasswordRequestParams(
    email = email,
    password = password,
    returnSecureToken = returnSecureToken
)