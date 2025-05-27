package ru.dreamteam.travelreminder.sync

import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.dao.SyncActionDao
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.local.model.ActionType
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.RemoteTravelRepository

class SyncManager(
    private val dao: SyncActionDao,
    private val remoteRepository: RemoteTravelRepository
) {
    suspend fun sync(progressCallback: (current: Int, total: Int) -> Unit = { _, _ -> }) {
        val actions = dao.getPendingActions()
        for (action in actions) {
            try {
                when (action.type) {
                    ActionType.ADD -> remoteRepository.addTravel(decode(action.payload))
                    ActionType.EDIT -> remoteRepository.editTravel(decode(action.payload))
                    ActionType.DELETE -> remoteRepository.deleteTravel(decode(action.payload))
                }
                dao.removeAction(action)
            } catch (e: Exception) {
                println("SyncManager" + e.message)
            }
        }
    }

    private fun decode(json: String): Travel {
        return Json.decodeFromString<TravelDto>(json).toDomain()
    }
}