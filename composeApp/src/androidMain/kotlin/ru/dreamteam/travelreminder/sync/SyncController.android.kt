package ru.dreamteam.travelreminder.sync

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

actual class SyncController(
    private val context: Context
) {
    actual fun startSync() {
        if (SyncService.isRunning.value == false) {
            println("SyncController   " + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
            val intent = Intent(context, SyncService::class.java).apply {
                action = SyncService.ACTION_START_SERVICE
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }

    actual fun stopSync() {
        if (SyncService.isRunning.value == true) {
            println("SyncController   " + "QQQQQQQQQQQQQQQQQQQQQQQQQQQ")
            val intent = Intent(context, SyncService::class.java).apply {
                action = SyncService.ACTION_STOP_SERVICE
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }
}