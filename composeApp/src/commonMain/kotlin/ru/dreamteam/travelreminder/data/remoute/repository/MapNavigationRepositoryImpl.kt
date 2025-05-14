package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import ru.dreamteam.travelreminder.domen.model.response.RouteResponse
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.repository.MapNavigationRepository

class MapNavigationRepositoryImpl(private val client: HttpClient): MapNavigationRepository {
    override suspend fun buildRoute(
        origin: Point,
        destination: Point,
        mode: TransportationMode
    ): List<Point> {
        val url = "https://routes.googleapis.com/directions/v2:computeRoutes" +
                "?key=AIzaSyBAHRxw9BMEMvBMv_lUwJ8uFd1QtW68Aiw"

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
            put("routingPreference", "TRAFFIC_AWARE")
        }



        val response: RouteResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(payload)
            header("X-Goog-FieldMask", "routes.distanceMeters,routes.duration,routes.polyline.encodedPolyline")
        }.body()

        val poly = response.routes.firstOrNull()?.polyline?.encodedPolyline ?: return emptyList()

        return decodePolyline(poly)
    }

    private fun decodePolyline(encoded: String): List<Point> {
        val poly = mutableListOf<Point>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = Point(
                lat / 1E5,
                lng / 1E5
            )
            poly.add(latLng)
        }

        return poly
    }
}