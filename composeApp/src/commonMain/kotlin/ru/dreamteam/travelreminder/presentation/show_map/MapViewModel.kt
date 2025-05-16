package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.data.local.model.map.Route
import ru.dreamteam.travelreminder.domen.model.Place
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.use_cases.GetNavigationRouteUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNearbyPlacesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceCoordinatesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceSuggestionUseCase
import kotlin.random.Random


class MapViewModel(
    private val getPlaceSuggestionUseCase: GetPlaceSuggestionUseCase,
    private val getNavigationRouteUseCase: GetNavigationRouteUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getPlaceCoordinatesUseCase: GetPlaceCoordinatesUseCase,
    localeProvider: LocaleProvider
) : ViewModel() {

    init {
        localeProvider.startLocationUpdate()
        localeProvider.setOnLocationChangedListener {
            _userLocation.value = it
        }
    }

    private val _placeSuggestionsQuery = mutableStateOf("")
    val placeSuggestionsQuery: State<String> = _placeSuggestionsQuery

    private val _suggestions = mutableStateOf<List<PlaceSuggestion>>(emptyList())
    val suggestions: State<List<PlaceSuggestion>> = _suggestions

    private var _route = mutableStateOf<Route?>(null)
    val route: State<Route?> = _route

    private var _userLocation = mutableStateOf<Point?>(null)
    val userLocation: State<Point?> = _userLocation

//    private var _originPlace = mutableStateOf<Place?>(null)
//    val originPlace: State<Place?> = _originPlace
//
//    private var _destinationPlace = mutableStateOf<Place?>(null)
//    val destinationPlace: State<Place?> = _destinationPlace

    private var _selectedPoints = mutableStateOf<Pair<Place?, Place?>>(Pair(null, null))
    val selectedPoints: State<Pair<Place?, Place?>> = _selectedPoints

    private var isOriginPoint = true
    private var sessionToken = Random.nextInt().toString()

    fun setPointSelectorAsOrigin() {
        isOriginPoint = true
    }

    fun setPointSelectorAsDestination() {
        isOriginPoint = false
    }

    fun onPlaceSuggestionsQueryTextChanged(text: String) {
        _placeSuggestionsQuery.value = text
        getPlaceSuggestions()
    }


    fun onClearQueryButtonPressed() {
        _placeSuggestionsQuery.value = ""
        _suggestions.value = emptyList()
    }

    fun onMapClicked(point: Point) {
        getNearbyPlacesUseCase(point).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    setPlaces(result.data)
                    if (selectedPoints.value.first != null && selectedPoints.value.second != null) {
                        getNavigationRouteUseCase(
                            origin = selectedPoints.value.first!!.point,
                            destination = selectedPoints.value.second!!.point,
                            mode = TransportationMode.DRIVE
                        ).onEach { routeResult ->
                            when (routeResult) {
                                is Resource.Error -> _route.value = null
                                is Resource.Loading -> _route.value = null
                                is Resource.Success -> _route.value = routeResult.data
                            }
                        }.launchIn(viewModelScope)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPlaceSuggestions() {
        getPlaceSuggestionUseCase.invoke(_placeSuggestionsQuery.value).onEach { result ->
            when (result) {
                is Resource.Error -> _suggestions.value = emptyList()
                is Resource.Loading -> _suggestions.value = emptyList()
                is Resource.Success -> _suggestions.value = result.data ?: emptyList()
            }
        }.launchIn(viewModelScope)
    }

    fun getPlaceCoordinates(placeSuggestion: PlaceSuggestion) {
        getPlaceCoordinatesUseCase.invoke(placeSuggestion).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> setPlaces(result.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun setPlaces(place: Place?) = if (isOriginPoint) _selectedPoints.value =
        Pair(place, _selectedPoints.value.second)
    else _selectedPoints.value =
        Pair(_selectedPoints.value.first, place)

}