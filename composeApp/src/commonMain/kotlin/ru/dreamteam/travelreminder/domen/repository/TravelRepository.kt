package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Travel

interface TravelRepository {
    suspend fun getTravels(): List<Travel>

    suspend fun deleteTravel(travel: Travel)

    suspend fun addTravel(travel: Travel)

    suspend fun editTravel(travel: Travel)
}