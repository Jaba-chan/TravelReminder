package ru.dreamteam.travelreminder.data.local.storage

import ru.dreamteam.travelreminder.BuildConfig

actual object SecretApiKeys {
    actual fun getFirebaseApiKey(): String = BuildConfig.FIREBASE_API_KEY
    actual fun getGoogleApiServicesKey(): String = BuildConfig.GOOGLE_API_SERVICES_KEY
}