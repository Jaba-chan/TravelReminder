package ru.dreamteam.travelreminder.data.local.repository

import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.mapper.toEntity
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.LocalTravelRepository
import ru.dreamteam.travelreminder.domen.repository.RemoteTravelRepository

class LocalTravelRepositoryImpl(
    private val dao: TravelsDao
) : LocalTravelRepository {

    override suspend fun getTravels(): List<Travel> = dao.getAll().map { it.toDomain() }

    override suspend fun getTravelById(travelId: String): Travel = dao.getById(travelId).toDomain()

    override suspend fun deleteTravel(travel: Travel) = dao.delete(travel.toEntity())

    override suspend fun addTravel(travel: Travel) = dao.insert(travel.toEntity())

    override suspend fun editTravel(travel: Travel) = dao.update(travel.toEntity())

    override suspend fun clearTables() = dao.clearTable()

    override suspend fun fillTables(travels: List<Travel>) = dao.fill(travels.map { it.toEntity() })
}