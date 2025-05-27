package ru.dreamteam.travelreminder.sync

import kotlinx.coroutines.flow.Flow

expect class NetworkConnectivityObserver {
    fun isConnected(): Boolean
    fun observe(): Flow<Boolean>
}