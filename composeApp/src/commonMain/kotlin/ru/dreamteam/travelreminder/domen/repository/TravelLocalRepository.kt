package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.travel.Travel

interface TravelLocalRepository {
    suspend fun getTravels(): List<Travel>
}