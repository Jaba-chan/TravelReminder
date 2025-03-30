package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.remoute.KtorClient
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelRepositoryImpl(private val storage: UserUidStorage) : TravelRepository {
    private val baseUrl =
        "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app/users/${storage.getUserUid()}/travels.json?auth=${storage.getIdToken()}"

    override suspend fun getTravels(): List<TravelDto> {
        val response: String = KtorClient.client.get(baseUrl).body()
        val map: Map<String, TravelDto> = Json.decodeFromString(response)
        return map.values.toList()
    }

    override fun deleteTravelById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addTravel(travel: TravelDto) {
        TODO("Not yet implemented")
    }

    override suspend fun editTravel(travel: TravelDto) {
        TODO("Not yet implemented")
    }

}