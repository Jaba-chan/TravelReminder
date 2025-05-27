package ru.dreamteam.travelreminder.sync

import kotlinx.coroutines.flow.Flow

actual class NetworkConnectivityObserver {
    actual fun isConnected(): Boolean {
        return true
    }

    actual fun observe(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}