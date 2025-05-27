package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.travel.Travel

interface SyncQueue {
    suspend fun enqueueAdd(travel: Travel)
    suspend fun enqueueEdit(travel: Travel)
    suspend fun enqueueDelete(travel: Travel)
}