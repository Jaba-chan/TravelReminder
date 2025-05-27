package ru.dreamteam.travelreminder.data.local.provider

import ru.dreamteam.travelreminder.domen.model.travel.Point

expect class LocaleProvider {
     fun startLocationUpdate()
     fun setOnLocationChangedListener(listener: (Point) -> Unit)
}