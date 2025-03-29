package ru.dreamteam.travelreminder.data.local.storage
import platform.Foundation.NSBundle

actual object FirebaseApiKeyProvider {
    actual fun getApiKey(): String {
        return NSBundle.mainBundle.infoDictionary?.get("API_KEY") as? String ?: ""
    }
}