package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailRequestParam
import ru.dreamteam.travelreminder.data.remoute.model.SignInByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailParam

fun ChangePasswordByEmailParam.toRequest(idToken: String, returnSecureToken: Boolean) = ChangePasswordByEmailRequestParam(
    idToken = idToken,
    password = newPassword,
    returnSecureToken = returnSecureToken
)

fun ChangePasswordByEmailParam.toSignInRequest(returnSecureToken: Boolean) = SignInByEmailAndPasswordRequestParams(
    email = email,
    password = oldPassword,
    returnSecureToken = returnSecureToken
)