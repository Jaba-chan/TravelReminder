package ru.dreamteam.travelreminder.data.repository

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.NotificationScheduler
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import kotlin.math.roundToLong

class NotifyingTravelRepository(
    private val delegate: TravelRepository,
    private val scheduler: NotificationScheduler
) : TravelRepository {

    override suspend fun addTravel(travel: Travel) {
        delegate.addTravel(travel)
        scheduler.scheduleNotification(travel.id, computeTime(travel))
    }

    override suspend fun editTravel(travel: Travel) {
        delegate.editTravel(travel)
        scheduler.scheduleNotification(travel.id, computeTime(travel))
    }

    override suspend fun syncRemoteToLocal() {
        delegate.syncRemoteToLocal()
    }

    override suspend fun deleteTravel(travel: Travel) {
        delegate.deleteTravel(travel)
        scheduler.cancelNotification(travel.id)
    }

    override suspend fun getTravels(): List<Travel> =
        delegate.getTravels()

    override suspend fun getTravelById(travelId: String): Travel =
        delegate.getTravelById(travelId)

    private fun computeTime(
        travel: Travel,
    ): Long {
        val tripLdt = LocalDateTime(
            year   = travel.date.year,
            month  = Month(travel.date.month),
            dayOfMonth = travel.date.day,
            hour   = travel.arrivalTime.hours,
            minute = travel.arrivalTime.minutes
        )
        val tripInstant: Instant = tripLdt.toInstant(timeZone = TimeZone.currentSystemDefault())

        val nowInstant: Instant = Clock.System.now()

        val travelDurationWithBuffer: Long =
            (travel.route.duration.removeSuffix("s").toLong() * 1.1).roundToLong()

        val remindBeforeSeconds: Long =
            travel.timeBeforeRemind.hours * 3_600L +
                    travel.timeBeforeRemind.minutes * 60L

        return (tripInstant.epochSeconds - nowInstant.epochSeconds) - travelDurationWithBuffer- remindBeforeSeconds
    }
}