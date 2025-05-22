package ru.dreamteam.travelreminder.data.remoute.repository

import androidx.compose.ui.text.intl.Locale
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import ru.dreamteam.travelreminder.data.local.model.map.Route
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.remoute.model.response.AutocompleteResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.response.NearbySearchResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.response.PlaceDetailsResponseDto
import ru.dreamteam.travelreminder.domen.model.Place
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.data.remoute.model.response.RouteResponse
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class MapRepositoryImpl(
    private val client: HttpClient,
    private val apiKey: String
) : MapRepository {
    private val regionCode = Locale.current.region
    override suspend fun buildRoute(
        origin: Point,
        destination: Point,
        mode: TransportationMode
    ): Route {
        val url = "https://routes.googleapis.com/directions/v2:computeRoutes" +
                "?key=$apiKey"

        val payload = buildJsonObject {
            put("origin", buildJsonObject {
                put("location", buildJsonObject {
                    put("latLng", buildJsonObject {
                        put("latitude", origin.latitude)
                        put("longitude", origin.longitude)
                    })
                })
            })
            put("destination", buildJsonObject {
                put("location", buildJsonObject {
                    put("latLng", buildJsonObject {
                        put("latitude", destination.latitude)
                        put("longitude", destination.longitude)
                    })
                })
            })
            put("travelMode", mode.name)
            if (mode == TransportationMode.DRIVE) {
                put("routingPreference", "TRAFFIC_AWARE")
            }
        }


        val response: RouteResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(payload)
            header(
                "X-Goog-FieldMask",
                "routes.distanceMeters,routes.duration,routes.polyline.encodedPolyline"
            )
        }.body()

        return response.routes.first().toDomain()
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getPlaceSuggestions(
        stringText: String
    ): List<PlaceSuggestion> {
        val url = "https://places.googleapis.com/v1/places:autocomplete?key=$apiKey"
        val requestBody = buildJsonObject {
            put("input", stringText)
            put("regionCode", regionCode)
            put("languageCode", regionCode)
            put("sessionToken", null)
        }

        val response: AutocompleteResponseDto = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

        return response.suggestions.mapNotNull { suggestion ->
            suggestion.placePrediction?.let { place ->
                PlaceSuggestion(
                    placeId = place.placeId,
                    title = place.structured.mainText.text,
                    description = place.structured.secondaryText.text
                )
            }
        }
    }

    override suspend fun getNearbyPlaces(point: Point): Place {
        val url = "https://places.googleapis.com/v1/places:searchNearby?key=$apiKey"

        val body = buildJsonObject {
            put("maxResultCount", 1)
            put("regionCode", regionCode)
            put("languageCode", regionCode)
            putJsonObject("locationRestriction") {
                putJsonObject("circle") {
                    putJsonObject("center") {
                        put("latitude", point.latitude)
                        put("longitude", point.longitude)
                    }
                    put("radius", 300)
                }
            }
        }
        val response: NearbySearchResponseDto = client.post(url) {
            header(
                "X-Goog-FieldMask",
                "places.location,places.name,places.displayName,places.formattedAddress"
            )
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

        val found = response.places.firstOrNull()

        val fallbackKey = "${point.latitude},${point.longitude}"

        return Place(
            placeId = found?.placeId ?: fallbackKey,
            title = found?.displayName?.text ?: fallbackKey,
            description = found?.formattedAddress ?: "",
            point = Point(
                found?.location?.latitude ?: point.latitude,
                found?.location?.longitude ?: point.longitude
            )
        )
    }

    override suspend fun getPlaceCoordinates(placeSuggestion: PlaceSuggestion): Place {
        val resourceId = placeSuggestion.placeId.substringAfter("places/")
        val url = "https://places.googleapis.com/v1/places/$resourceId?key=$apiKey"

        val response: PlaceDetailsResponseDto = client.get(url) {
            header("X-Goog-FieldMask", "location")
        }.body()
        val location = response.location

        return Place(
            placeId = placeSuggestion.placeId,
            title = placeSuggestion.title,
            description = placeSuggestion.description,
            point = Point(location.latitude, location.longitude)
        )
    }
}