package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
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
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class TravelRepositoryImpl(
    private val client: HttpClient,
    private val storage: UserUidStorage,
) : TravelRepository {

    private val baseUrl =
        "https://travel-reminder-a8175-default-rtdb.europe-west1.firebasedatabase.app"
    private val userPath get() = "users/${storage.getUserUid()}"
    private val authQuery get() = "auth=${storage.getIdToken()}"

    private fun travelUrl(travelId: String? = null): String {
        val path = when (travelId) {
            null -> "$userPath/travels.json"
            else -> "$userPath/travels/$travelId.json"
        }
        return "$baseUrl/$path?$authQuery"
    }

    override suspend fun getTravels(): List<Travel> {
        val url = travelUrl()
        val rawJson: String = client
            .get(url)
            .body()

        if (rawJson.trim() == "null") return emptyList()

        val dtoMap: Map<String, TravelDto> = Json.decodeFromString(rawJson)
        return dtoMap.values.map(TravelDto::toDomain)
    }

    override suspend fun getTravelById(travelId: String): Travel {
        val url = travelUrl(travelId)
        val rawJson = client
            .get(url)
            .body<String>()

        val dto = Json.decodeFromString<TravelDto>(rawJson)
        return dto.toDomain()
    }

    override suspend fun deleteTravel(travel: Travel) {
        client.delete(travelUrl(travel.id))
    }

    override suspend fun addTravel(travel: Travel) {
        client.put(travelUrl(travel.id)) {
            contentType(ContentType.Application.Json)
            setBody(travel.toDto())
        }
    }

    override suspend fun editTravel(travel: Travel) {
        client.patch(travelUrl(travel.id)) {
            contentType(ContentType.Application.Json)
            setBody(travel.toDto())
        }
    }
}