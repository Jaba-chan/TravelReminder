package ru.dreamteam.travelreminder.data.local.repository

import ru.dreamteam.travelreminder.data.local.room_db.TravelsDatabase
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelLocalRepository

class TravelLocalRepositoryImpl(private val travelsDatabase: TravelsDatabase): TravelLocalRepository{
    override suspend fun getTravels(): List<Travel> {
        return travelsDatabase.travelsDao().getAllUserId().map {it.toDomain()}
    }
}