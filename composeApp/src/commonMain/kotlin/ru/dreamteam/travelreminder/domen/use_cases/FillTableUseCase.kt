package ru.dreamteam.travelreminder.domen.use_cases

import ru.dreamteam.travelreminder.domen.repository.LocalTravelRepository
import ru.dreamteam.travelreminder.domen.repository.RemoteTravelRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class FillTableUseCase(
    private val travelRepository: TravelRepository,
){
    suspend operator fun invoke(){
        travelRepository.syncRemoteToLocal()
    }
}