package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.Travel

interface TravelRepository {
    fun getTravels(): List<Travel>

    fun deleteTravelById(id: Int)

    suspend fun addTravel(travel: Travel)

    suspend fun editTravel(travel: Travel)
}