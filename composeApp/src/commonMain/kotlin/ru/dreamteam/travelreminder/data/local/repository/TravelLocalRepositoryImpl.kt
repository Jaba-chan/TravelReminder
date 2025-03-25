package ru.dreamteam.travelreminder.data.local.repository

import ru.dreamteam.travelreminder.domen.model.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelLocalRepositoryImpl: TravelRepository {
    override suspend fun getTravels(): List<Travel> {

    }

    override fun deleteTravelById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun editTravel(travel: Travel): Travel {
        TODO("Not yet implemented")
    }
}