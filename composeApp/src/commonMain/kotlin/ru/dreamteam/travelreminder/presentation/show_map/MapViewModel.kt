package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.repository.MapNavigationRepository


class MapViewModel(
    private val repository: MapNavigationRepository,
    localeProvider: LocaleProvider
) : ViewModel() {

    init {
        localeProvider.startLocationUpdate()
        localeProvider.setOnLocationChangedListener {
            _userLocation.value = it
        }
    }

    private var _polyline = mutableStateOf<List<Point>>(emptyList())
    val polyline: State<List<Point>> = _polyline

    private var _userLocation = mutableStateOf<Point?>(null)
    val userLocation: State<Point?> = _userLocation

    var isOriginPoint = true

    fun setPointSelectorAsOrigin() {
        isOriginPoint = true
    }

    fun setPointSelectorAsDestination() {
        isOriginPoint = false
    }


    fun getRoute(
        origin: Point,
        destination: Point,
        mode: TransportationMode
    ) {
        viewModelScope.launch {
            val result =
                repository.buildRoute(origin = origin, destination = destination, mode = mode)
            _polyline.value = result
        }
    }
}