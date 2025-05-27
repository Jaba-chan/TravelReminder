package ru.dreamteam.travelreminder.domen.use_cases

import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.LocalTravelRepository

class LogOutUseCase(
    private val authRepository: AuthRepository,
    private val localTravelRepository: LocalTravelRepository
) {
    suspend operator fun invoke(){
        authRepository.logOut()
        localTravelRepository.clearTables()
    }
}