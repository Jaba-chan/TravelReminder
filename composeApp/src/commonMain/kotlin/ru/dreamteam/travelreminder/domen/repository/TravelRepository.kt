package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.travel.Travel

interface TravelRepository {
    suspend fun getTravels(): List<Travel>

    suspend fun getTravelById(travelId: String): Travel

    suspend fun deleteTravel(travel: Travel)

    suspend fun addTravel(travel: Travel)

    suspend fun editTravel(travel: Travel)
}