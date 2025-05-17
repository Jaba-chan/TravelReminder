package ru.dreamteam.travelreminder.data.local.storage

expect object SecretApiKeys {
    fun getFirebaseApiKey(): String
    fun getGoogleApiServicesKey(): String
}

fun provideFirebaseApiKey(): SecretApiKeys {
    return SecretApiKeys
}