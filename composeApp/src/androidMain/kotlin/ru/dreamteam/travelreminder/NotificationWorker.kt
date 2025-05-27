package ru.dreamteam.travelreminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val travelId = inputData.getString("TRAVEL_ID") ?: return Result.failure()
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_MSG_ID,
                    applicationContext.getString(R.string.travel_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val notification = NotificationCompat.Builder(applicationContext, "travel_reminder")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Пора в путешествие!")
            .setContentText("Ваше путешествие начинается сейчас.")
            .setAutoCancel(true)
            .build()
        notificationManager.notify(travelId.hashCode(), notification)
        return Result.success()
    }

    companion object {
        private const val CHANNEL_MSG_ID = "travel_reminder"
    }
}