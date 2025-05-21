package ru.dreamteam.travelreminder.domen.use_cases

import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class CheckFirstLaunchUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean = repository.isFirstLaunch()
}