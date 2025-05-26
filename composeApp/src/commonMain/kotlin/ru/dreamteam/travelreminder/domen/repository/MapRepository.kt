package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.travel.Route
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode

interface MapRepository {
    suspend fun buildRoute(origin: Point,
                           destination: Point,
                           mode: TransportationMode
    ): Route

    suspend fun getPlaceSuggestions(stringText: String): List<PlaceSuggestion>
    suspend fun getNearbyPlaces(point: Point): Place
    suspend fun getPlaceCoordinates(placeSuggestion: PlaceSuggestion): Place
}