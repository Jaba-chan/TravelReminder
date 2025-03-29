package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.remoute.model.SignInResponseDao
import ru.dreamteam.travelreminder.domen.model.SignInWithEmailAndPasswordParams

interface AuthRepository {
    suspend fun singInByEmailAndPassword(params: SignInWithEmailAndPasswordParams): SignInResponseDao
}