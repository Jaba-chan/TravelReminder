package ru.dreamteam.travelreminder.domen.repository

import kotlinx.coroutines.flow.Flow

actual class NetworkConnectivityObserver {
    actual fun isConnected(): Boolean {
        return true
    }

    actual fun observe(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}