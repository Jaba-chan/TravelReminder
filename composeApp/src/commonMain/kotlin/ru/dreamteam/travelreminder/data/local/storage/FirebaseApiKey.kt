package ru.dreamteam.travelreminder.data.local.storage

expect object FirebaseApiKey {
    fun getApiKey(): String
}

fun provideFirebaseApiKey(): FirebaseApiKey {
    return FirebaseApiKey
}