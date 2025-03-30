package ru.dreamteam.travelreminder.data.local.repository

import ru.dreamteam.travelreminder.data.local.model.TravelEntity
import ru.dreamteam.travelreminder.data.local.room_db.TravelsDatabase
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.mapper.toEntity
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelLocalRepositoryImpl(private val travelsDatabase: TravelsDatabase): TravelRepository {

    override suspend fun getTravels(): List<TravelDto> {
        return travelsDatabase.travelsDao().getAllUserId().map(TravelEntity::toDomain)
    }

    override fun deleteTravelById(id: Int) {
        travelsDatabase.travelsDao().deleteById(id)
    }

    override suspend fun addTravel(travel: TravelDto) {
        travelsDatabase.travelsDao().insert(travel.toEntity())
    }

    override suspend fun editTravel(travel: TravelDto) {
        travelsDatabase.travelsDao().edit(travel.toEntity())
    }
}