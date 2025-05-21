package ru.dreamteam.travelreminder.data.mapper.params

import ru.dreamteam.travelreminder.data.remoute.model.params.ChangePasswordByEmailRequestParam
import ru.dreamteam.travelreminder.data.remoute.model.params.SignInByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam

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