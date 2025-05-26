package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.travel.RouteSaveDto
import ru.dreamteam.travelreminder.domen.model.travel.Route
import ru.dreamteam.travelreminder.data.remoute.model.response.RouteDto
import ru.dreamteam.travelreminder.domen.model.travel.Point

fun RouteDto.toDomain() = Route(
    poly = decodePolyline(polyline.encodedPolyline),
    duration = duration,
    distance = distanceMeters
)

fun RouteSaveDto.toDomain() = Route(
    poly = poly.map { it.toDomain() },
    duration = duration,
    distance = distance
)

fun Route.toDto() = RouteSaveDto(
    poly = poly.map { it.toDto() },
    duration = duration,
    distance = distance
)

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