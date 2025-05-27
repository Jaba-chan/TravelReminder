package ru.dreamteam.travelreminder.data.repository

import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.LocalTravelRepository
import ru.dreamteam.travelreminder.sync.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.domen.repository.RemoteTravelRepository
import ru.dreamteam.travelreminder.domen.repository.SyncQueue
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class DefaultTravelRepository(
    private val localRepository: LocalTravelRepository,
    private val remoteTravelRepository: RemoteTravelRepository,
    private val syncQueue: SyncQueue,
    private val networkObserver: NetworkConnectivityObserver,
) : TravelRepository {

    override suspend fun getTravels(): List<Travel> {
        if (networkObserver.isConnected()) {
            return remoteTravelRepository.getTravels()
        }
        return localRepository.getTravels()
    }

    override suspend fun getTravelById(travelId: String): Travel {
        if (networkObserver.isConnected()) {
            return remoteTravelRepository.getTravelById(travelId)
        }
        return localRepository.getTravelById(travelId)
    }

    override suspend fun deleteTravel(travel: Travel) {
        if (networkObserver.isConnected()) {
            remoteTravelRepository.deleteTravel(travel)
        } else {
            syncQueue.enqueueDelete(travel)
        }

        localRepository.deleteTravel(travel)
    }

    override suspend fun addTravel(travel: Travel) {
        if (networkObserver.isConnected()) {
            remoteTravelRepository.addTravel(travel)
        } else {
            syncQueue.enqueueAdd(travel)
        }

        localRepository.addTravel(travel)
    }

    override suspend fun editTravel(travel: Travel) {
        if (networkObserver.isConnected()) {
            remoteTravelRepository.editTravel(travel)
        } else {
            syncQueue.enqueueEdit(travel)
        }

        localRepository.editTravel(travel)
    }

    override suspend fun syncRemoteToLocal() {
        val travels = remoteTravelRepository.getTravels()
        localRepository.fillTables(travels)
    }
}