package ru.dreamteam.travelreminder.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity(tableName = "sync_actions")
data class SyncActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: ActionType,
    val payload: String,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)