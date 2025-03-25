package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.Travel

interface TravelRepository {
    suspend fun getTravels(): List<Travel>

    fun deleteTravelById(id: Int)

    fun editTravel(travel: Travel): Travel
}