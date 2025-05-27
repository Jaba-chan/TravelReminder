package ru.dreamteam.travelreminder.sync

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.dreamteam.travelreminder.R

class SyncService: LifecycleService() {
    private val syncManager: SyncManager by inject()
    private lateinit var notificationManager: NotificationManagerCompat
    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        notificationManager = NotificationManagerCompat.from(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when(intent?.action){
            ACTION_START_SERVICE -> if (isRunning.value == false) start()
            ACTION_STOP_SERVICE -> if (isRunning.value == true) stop()
        }
        return START_STICKY
    }

    private fun start(){
        isRunning.postValue(true)
        notificationManager = NotificationManagerCompat.from(this)
        startForeground(NOTIFICATION_ID, createProgressNotification(0, 100))

        job = CoroutineScope(Dispatchers.IO).launch {
            syncManager.sync(
                progressCallback = { current, total ->
                    createProgressNotification(current, total)
                }
            )
            stop()
        }
    }

    private fun stop(){
        isRunning.postValue(false)
        stopSelf()
    }

    private fun createProgressNotification( progress: Int, max: Int): Notification {
        return NotificationCompat.Builder(this, CHANNEL_MSG_ID)
            .setContentTitle(getString(R.string.sync_data))
            .setContentText(getString(R.string.sync_data_progress) + " $progress из $max")
            .setSmallIcon(R.drawable.ic_sync)
            .setProgress(max, progress, false)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_MSG_ID,
                getString(R.string.sync_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.sync_notification_channel_description)
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    companion object {
        val isRunning = MutableLiveData(false)
        private const val CHANNEL_MSG_ID = "sync_notification_channel"
        private const val NOTIFICATION_ID = 1
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE "
    }
}