package ru.dreamteam.travelreminder.data.local.storage

import ru.dreamteam.travelreminder.BuildConfig

actual object FirebaseApiKeyProvider {
    actual fun getApiKey(): String = BuildConfig.FIREBASE_API_KEY
}