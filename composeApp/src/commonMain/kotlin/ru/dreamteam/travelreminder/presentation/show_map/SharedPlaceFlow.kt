package ru.dreamteam.travelreminder.presentation.show_map

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode

class SharedPlaceFlow {
    private val _places = MutableSharedFlow<Pair<Place?, Place?>>(
        extraBufferCapacity = 1,
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val _mode = MutableSharedFlow<TransportationMode>(
        extraBufferCapacity = 1,
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val places = _places.asSharedFlow()
    val mode = _mode.asSharedFlow()

    fun emit(places: Pair<Place?, Place?>) {
        _places.tryEmit(places)
    }

    fun emit(mode: TransportationMode) {
        _mode.tryEmit(mode)
    }
}