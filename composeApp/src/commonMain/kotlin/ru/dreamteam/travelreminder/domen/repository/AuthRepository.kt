package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.local.model.map.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.data.remoute.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.data.remoute.model.response.SignInResponse
import ru.dreamteam.travelreminder.data.remoute.model.response.SignUpResponse

interface AuthRepository {
    fun isFirstLaunch(): Boolean

    fun logOut()

    suspend fun refreshToken(refreshToken: String?)

    suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams)

    suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams)

    suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam)
}