package ru.dreamteam.travelreminder.data.mapper

import androidx.room.TypeConverter
import ru.dreamteam.travelreminder.data.remoute.model.travel.TransportationModeDto

class TransportationModeConverter {
    @TypeConverter
    fun fromTransportationMode(transportationMode: TransportationModeDto): String {
        return transportationMode.name
    }

    @TypeConverter
    fun toTransportationMode(str: String): TransportationModeDto {
        return enumValueOf(str)
    }
}