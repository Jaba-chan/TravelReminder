package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto

interface TravelRepository {
    suspend fun getTravels(): List<TravelDto>

    fun deleteTravelById(id: Int)

    suspend fun addTravel(travel: TravelDto)

    suspend fun editTravel(travel: TravelDto)
}