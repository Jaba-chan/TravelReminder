package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailResultDto
import ru.dreamteam.travelreminder.data.remoute.model.SignInResultDto
import ru.dreamteam.travelreminder.data.remoute.model.SignUpResultDto
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.SignUpByEmailAndPasswordParams

interface AuthRepository {
    suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams): SignInResultDto
    suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams): SignUpResultDto
    suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam): ChangePasswordByEmailResultDto
}