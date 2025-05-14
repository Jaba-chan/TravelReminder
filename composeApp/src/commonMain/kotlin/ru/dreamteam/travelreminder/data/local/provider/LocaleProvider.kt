package ru.dreamteam.travelreminder.data.local.provider

import ru.dreamteam.travelreminder.domen.model.travel.Point

abstract class LocaleProvider {
    abstract fun startLocationUpdate()
    abstract fun setOnLocationChangedListener(listener: (Point) -> Unit)
}