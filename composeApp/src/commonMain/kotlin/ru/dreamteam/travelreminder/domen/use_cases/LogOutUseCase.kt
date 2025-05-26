package ru.dreamteam.travelreminder.domen.use_cases

import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class LogOutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logOut()
}