package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.data.remoute.model.Travel

interface TravelRepository {
    suspend fun getTravels(): List<Travel>

    fun deleteTravelById(id: Int)

    suspend fun addTravel(travel: Travel)

    suspend fun editTravel(travel: Travel)
}