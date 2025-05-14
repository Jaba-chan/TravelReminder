package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.domen.model.response.SignInResponse
import ru.dreamteam.travelreminder.domen.model.response.SignUpResponse

interface AuthRepository {
    fun isFirstLaunch(): Boolean

    suspend fun refreshToken(refreshToken: String?)

    suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams): ResponseResult<SignInResponse>

    suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams): ResponseResult<SignUpResponse>

    suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam): ResponseResult<ChangePasswordByEmailResponse>
}