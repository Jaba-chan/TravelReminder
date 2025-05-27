package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams

interface AuthRepository {
    fun isFirstLaunch(): Boolean

    suspend fun logOut()

    suspend fun refreshToken(refreshToken: String?)

    suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams)

    suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams)

    suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam)
}