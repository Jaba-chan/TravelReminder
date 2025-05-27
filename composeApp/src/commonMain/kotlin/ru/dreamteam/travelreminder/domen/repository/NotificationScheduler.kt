package ru.dreamteam.travelreminder.domen.repository

expect class NotificationScheduler {
    fun scheduleNotification(travelId: String, timeMillis: Long)

    fun cancelNotification(travelId: String)
}