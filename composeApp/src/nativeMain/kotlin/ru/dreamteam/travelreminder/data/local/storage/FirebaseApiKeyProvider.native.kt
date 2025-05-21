package ru.dreamteam.travelreminder.data.local.storage
import platform.Foundation.NSBundle

actual object SecretApiKeys {
    actual fun getFirebaseApiKey(): String {
        return NSBundle.mainBundle.infoDictionary?.get("API_KEY") as? String ?: ""
    }
    actual fun getGoogleApiServicesKey(): String {
        return NSBundle.mainBundle.infoDictionary?.get("API_KEY") as? String ?: ""
    }
}