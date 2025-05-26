package ru.dreamteam.travelreminder.domen.repository

import kotlinx.coroutines.flow.Flow

expect class NetworkConnectivityObserver {
    fun isConnected(): Boolean
    fun observe(): Flow<Boolean>
}