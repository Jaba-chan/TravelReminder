package ru.dreamteam.travelreminder.domen.repository

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import ru.dreamteam.travelreminder.NotificationWorker
import java.util.concurrent.TimeUnit

actual class NotificationScheduler(
    private val workManager: WorkManager
) {

    actual fun scheduleNotification(travelId: String, timeMillis: Long) {
        val data = workDataOf(
            "TRAVEL_ID" to travelId
        )
        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeMillis, TimeUnit.SECONDS)
            .setInputData(data)
            .addTag(travelId)
            .build()

        workManager.enqueueUniqueWork(
            travelId,
            ExistingWorkPolicy.REPLACE,
            work
        )
    }

    actual fun cancelNotification(travelId: String) {
        workManager.cancelAllWorkByTag(travelId)
    }
}