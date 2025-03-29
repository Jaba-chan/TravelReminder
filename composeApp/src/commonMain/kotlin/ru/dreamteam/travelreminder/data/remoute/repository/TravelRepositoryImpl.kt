package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.remoute.KtorClient
import ru.dreamteam.travelreminder.data.remoute.model.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelRepositoryImpl(private val storage: UserUidStorage) : TravelRepository {
    private val baseUrl =
        "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app/users/${storage.getUserUid()}/travels.json?auth=${storage.getIdToken()}"

    override suspend fun getTravels(): List<Travel> {
        val response: String = KtorClient.client.get(baseUrl).body()
        val map: Map<String, Travel> = Json.decodeFromString(response)
        val list = map.values.toList()
        return list
    }

    override fun deleteTravelById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addTravel(travel: Travel) {
        TODO("Not yet implemented")
    }

    override suspend fun editTravel(travel: Travel) {
        TODO("Not yet implemented")
    }

}