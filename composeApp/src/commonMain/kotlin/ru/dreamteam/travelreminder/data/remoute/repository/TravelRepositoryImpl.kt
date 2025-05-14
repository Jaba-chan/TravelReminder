package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.mapper.toDto
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository


class TravelRepositoryImpl(
    private val client: HttpClient,
    private val storage: UserUidStorage,
) : TravelRepository {

    override suspend fun getTravels(): List<Travel> {
        val getUrl =
            "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app/users/${storage.getUserUid()}/travels.json?auth=${storage.getIdToken()}"

        val stringResponse: String = client.get(getUrl).body()
        val struct: Map<String, TravelDto> = Json.decodeFromString(stringResponse)
        return struct.values.toList().map { it.toDomain() }
    }

    override suspend fun deleteTravel(travel: Travel) {
        val deleteUrl =
            "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app/users/${storage.getUserUid()}/travels/${travel.id}.json?auth=${storage.getIdToken()}"
        client.delete(deleteUrl)
    }

    override suspend fun addTravel(travel: Travel) {
        val addUrl =
            "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app/users/${storage.getUserUid()}/travels/${travel.id}.json?auth=${storage.getIdToken()}"
        println("TravelRepositoryImpl: add button pressed")
        client.put(addUrl) {
            contentType(ContentType.Application.Json)
            setBody(travel.toDto())
        }
    }

    override suspend fun editTravel(travel: Travel) {
        TODO("Not yet implemented")
    }

}