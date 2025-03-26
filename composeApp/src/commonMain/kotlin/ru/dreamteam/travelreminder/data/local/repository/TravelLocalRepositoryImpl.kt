package ru.dreamteam.travelreminder.data.local.repository

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.local.room_db.TravelsDatabase
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.mapper.toEntity
import ru.dreamteam.travelreminder.domen.model.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelLocalRepositoryImpl(private val travelsDatabase: TravelsDatabase): TravelRepository {

    override fun getTravels(): List<Travel> {
        return travelsDatabase.travelsDao().getAllUserId().map(TravelEntity::toDomain)
    }

    override fun deleteTravelById(id: Int) {
        travelsDatabase.travelsDao().deleteById(id)
    }

    override suspend fun addTravel(travel: Travel) {
        travelsDatabase.travelsDao().insert(travel)
    }

    override suspend fun editTravel(travel: Travel) {
        travelsDatabase.travelsDao().edit(travel.toEntity())
    }
}