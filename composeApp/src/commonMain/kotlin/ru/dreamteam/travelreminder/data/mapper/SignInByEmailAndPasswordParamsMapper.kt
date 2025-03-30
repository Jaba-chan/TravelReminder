package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.SignInByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.SignInByEmailAndPasswordParams

fun SignInByEmailAndPasswordParams.toRequest(returnSecureToken: Boolean): SignInByEmailAndPasswordRequestParams = SignInByEmailAndPasswordRequestParams(
    email = email,
    password = password,
    returnSecureToken = returnSecureToken
)