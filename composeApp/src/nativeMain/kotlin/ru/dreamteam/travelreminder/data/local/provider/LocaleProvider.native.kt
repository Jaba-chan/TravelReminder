package ru.dreamteam.travelreminder.data.local.provider

import ru.dreamteam.travelreminder.domen.model.travel.Point

actual class LocaleProvider {
    actual fun startLocationUpdate() {
    }

    actual fun setOnLocationChangedListener(listener: (Point) -> Unit) {
    }
}