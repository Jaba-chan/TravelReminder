package ru.dreamteam.travelreminder.data.local.repository

import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.dao.SyncActionDao
import ru.dreamteam.travelreminder.data.local.model.ActionType
import ru.dreamteam.travelreminder.data.local.model.SyncActionEntity
import ru.dreamteam.travelreminder.data.mapper.toDto
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.SyncQueue

class RoomSyncQueue(
    private val dao: SyncActionDao
) : SyncQueue {
    override suspend fun enqueueAdd(travel: Travel) {
        dao.enqueueAction(
            SyncActionEntity(
                payload = Json.encodeToString(travel.toDto()),
                type = ActionType.ADD
            )
        )
    }

    override suspend fun enqueueEdit(travel: Travel) {
        dao.enqueueAction(
            SyncActionEntity(
                payload = Json.encodeToString(travel.toDto()),
                type = ActionType.EDIT
            )
        )
    }

    override suspend fun enqueueDelete(travel: Travel) {
        dao.enqueueAction(
            SyncActionEntity(
                payload = Json.encodeToString(travel.toDto()),
                type = ActionType.DELETE
            )
        )
    }
}