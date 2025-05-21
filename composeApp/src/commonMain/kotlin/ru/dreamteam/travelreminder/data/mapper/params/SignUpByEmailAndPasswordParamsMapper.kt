package ru.dreamteam.travelreminder.data.mapper.params

import ru.dreamteam.travelreminder.data.remoute.model.params.SignUpByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams

fun SignUpByEmailAndPasswordParams.toRequest(returnSecureToken: Boolean): SignUpByEmailAndPasswordRequestParams = SignUpByEmailAndPasswordRequestParams(
    email = email,
    password = password,
    returnSecureToken = returnSecureToken
)