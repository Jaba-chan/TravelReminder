package ru.dreamteam.travelreminder.data.local.storage

expect object FirebaseApiKeyProvider {
    fun getApiKey(): String
}