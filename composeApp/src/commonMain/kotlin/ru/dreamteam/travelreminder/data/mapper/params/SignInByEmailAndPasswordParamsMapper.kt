package ru.dreamteam.travelreminder.data.mapper.params

import ru.dreamteam.travelreminder.data.remoute.model.params.SignInByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams

fun SignInByEmailAndPasswordParams.toRequest(returnSecureToken: Boolean): SignInByEmailAndPasswordRequestParams = SignInByEmailAndPasswordRequestParams(
    email = email,
    password = password,
    returnSecureToken = returnSecureToken
)