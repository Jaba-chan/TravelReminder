package ru.dreamteam.travelreminder.domen.repository

import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode

interface MapNavigationRepository {
    suspend fun buildRoute(origin: Point,
                           destination: Point,
                           mode: TransportationMode
    ): List<Point>
}