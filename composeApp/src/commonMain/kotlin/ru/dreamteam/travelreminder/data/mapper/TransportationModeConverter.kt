package ru.dreamteam.travelreminder.data.mapper

import androidx.room.TypeConverter
import ru.dreamteam.travelreminder.domen.model.TransportationMode

class TransportationModeConverter {
    @TypeConverter
    fun fromTransportationMode(transportationMode: TransportationMode): String {
        return transportationMode.name
    }

    @TypeConverter
    fun toTransportationMode(str: String): TransportationMode {
        return enumValueOf(str)
    }
}