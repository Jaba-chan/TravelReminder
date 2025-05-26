package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Route
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.domen.model.travel.Date
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.Time
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.EditTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNavigationRouteUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNearbyPlacesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceCoordinatesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceSuggestionUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelByIdUseCase
import ru.dreamteam.travelreminder.presentation.DefaultErrorMapper

class AddTravelViewModel(
    private val addTravelUseCase: AddTravelUseCase,
    private val getTravelByIdUseCase: GetTravelByIdUseCase,
    private val editTravelUseCase: EditTravelUseCase,
    private val defaultErrorMapper: DefaultErrorMapper,
    private val getPlaceSuggestionUseCase: GetPlaceSuggestionUseCase,
    private val getNavigationRouteUseCase: GetNavigationRouteUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getPlaceCoordinatesUseCase: GetPlaceCoordinatesUseCase,
    localeProvider: LocaleProvider,
) : ViewModel() {

    init {
        localeProvider.startLocationUpdate()
        localeProvider.setOnLocationChangedListener {
            _userLocation.value = it
        }
    }
    private var isOriginPoint = true
    private var isReversed = false

    private val _state = MutableStateFlow<AddTravelState>(AddTravelState.Idle)
    val state = _state.asStateFlow()

    //UI
    private val _snackBarMessages = MutableSharedFlow<String>()
    val snackBarMessages = _snackBarMessages.asSharedFlow()

    private val _isDatePickerVisible = mutableStateOf(false)
    val isDatePickerVisible: State<Boolean> = _isDatePickerVisible

    private val _isTimePickerVisible = mutableStateOf(false)
    val isTimePickerVisible: State<Boolean> = _isTimePickerVisible

    private val _isTimeRemindPickerVisible = mutableStateOf(false)
    val isTimeRemindPickerVisible: State<Boolean> = _isTimeRemindPickerVisible

    private val _fieldErrors =
        mutableStateOf<Map<FieldKey, CaughtError>>(emptyMap())
    val fieldErrors: State<Map<FieldKey, CaughtError>> = _fieldErrors

    private val _editedTravelId = mutableStateOf<String?>(null)
    val editedTravelId: State<String?> = _editedTravelId

    //Fields
    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName

    private val _travelDate = mutableStateOf<Date?>(null)
    val travelDate: State<Date?> = _travelDate

    private val _arrivalTime = mutableStateOf<Time?>(null)
    val arrivalTime: State<Time?> = _arrivalTime

    private val _timeBeforeRemind = mutableStateOf<Time?>(null)
    val timeBeforeRemind: State<Time?> = _timeBeforeRemind

    private val _transportationMode = mutableStateOf(TransportationMode.DRIVE)
    val transportationMode: State<TransportationMode> = _transportationMode

    private var _selectedPoints = mutableStateOf<Pair<Place?, Place?>>(Pair(null, null))
    val selectedPoints: State<Pair<Place?, Place?>> = _selectedPoints

    private val _placeSuggestionsQuery = mutableStateOf("")
    val placeSuggestionsQuery: State<String> = _placeSuggestionsQuery

    private val _suggestions = mutableStateOf<List<PlaceSuggestion>>(emptyList())
    val suggestions: State<List<PlaceSuggestion>> = _suggestions

    private var _route = mutableStateOf<Route?>(null)
    val route: State<Route?> = _route

    private var _userLocation = mutableStateOf<Point?>(null)
    val userLocation: State<Point?> = _userLocation

    fun showSnackBarMessage(message: String) {
        viewModelScope.launch {
            _snackBarMessages.emit(message)
        }
    }

    fun onSaveButtonPressed() {
        validate().let {
            if (it != null)
                if (_editedTravelId.value == null){
                    addTravelUseCase(it)
                        .onEach { result ->
                            when (result) {
                                is Resource.Error -> _state.value = AddTravelState.Error(result.error)
                                is Resource.Loading -> _state.value = AddTravelState.Loading
                                is Resource.Success -> _state.value = AddTravelState.Success
                            }
                        }.launchIn(viewModelScope)
                } else {
                    editTravelUseCase(it)
                        .onEach { result ->
                            when (result) {
                                is Resource.Error -> _state.value =
                                    AddTravelState.Error(result.error)

                                is Resource.Loading -> _state.value = AddTravelState.Loading
                                is Resource.Success -> _state.value = AddTravelState.Success
                            }
                        }.launchIn(viewModelScope)
                    resetFields()
                }
        }
    }

    private fun initEdit(travel: Travel) {
        _travelName.value = travel.title
        _travelDate.value = travel.date
        _arrivalTime.value = travel.arrivalTime
        _timeBeforeRemind.value = travel.timeBeforeRemind
        _selectedPoints.value = Pair(travel.startPlace, travel.destinationPlace)
        _transportationMode.value = travel.transportationMode
        _route.value = travel.route
    }

    fun resetFields() {
        _travelName.value = ""
        _travelDate.value = null
        _arrivalTime.value = null
        _timeBeforeRemind.value = null
        _selectedPoints.value = Pair(null, null)
        _transportationMode.value = TransportationMode.DRIVE
        _route.value = null
    }

    fun setAsEditMode(travelId: String) {
        getTravelByIdUseCase(travelId)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        initEdit(result.data)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        _editedTravelId.value = travelId
    }

    fun resetErrors() {
        _fieldErrors.value = emptyMap()
    }

    fun setStateToIdle() {
        _state.value = AddTravelState.Idle
    }

    fun showDatePicker() {
        _isDatePickerVisible.value = true
    }

    fun hideDatePicker() {
        _isDatePickerVisible.value = false
    }

    fun showTimePicker() {
        _isTimePickerVisible.value = true
    }

    fun showTimeRemindPicker() {
        _isTimeRemindPickerVisible.value = true
    }

    fun hideTimePicker() {
        _isTimePickerVisible.value = false
    }

    fun hideTimeRemindPicker() {
        _isTimeRemindPickerVisible.value = false
    }

    fun setTravelDate(date: Date?) {
        _travelDate.value = date
        if (date != null)
            _fieldErrors.value -= FieldKey.DATE
    }

    fun setTravelTime(time: Time) {
        _arrivalTime.value = time
        _fieldErrors.value -= FieldKey.TIME
    }

    fun setTimeBeforeRemind(time: Time) {
        _timeBeforeRemind.value = time
        _fieldErrors.value -= FieldKey.TIME_BEFORE_REMIND
    }

    fun onTravelNameTextChanged(newText: String) {
        _travelName.value = newText
        if (newText.isNotBlank()) _fieldErrors.value -= FieldKey.NAME
    }

    fun resetNoRouteError() {
        _fieldErrors.value -= FieldKey.ROUTE
    }

    private fun validate(): Travel? {
        val name = _travelName.value.trim()
        val time = _arrivalTime.value
        val timeBeforeRemind = _timeBeforeRemind.value
        val date = _travelDate.value
        val startPlace = _selectedPoints.value.first
        val destinationPlace = _selectedPoints.value.second
        val mode = _transportationMode.value
        val route = _route.value

        val errors = buildMap {
            if (name.isEmpty()) {
                put(
                    FieldKey.NAME,
                    defaultErrorMapper.map(AddTravelFieldsValidationErrors.EMPTY_NAME)
                )
            }
            if (route == null) {
                put(
                    FieldKey.ROUTE,
                    defaultErrorMapper.map(AddTravelFieldsValidationErrors.NO_ROUTE)
                )
            }
            if (date == null) {
                put(
                    FieldKey.DATE,
                    defaultErrorMapper.map(AddTravelFieldsValidationErrors.EMPTY_DATE)
                )
            }
            if (time == null) {
                put(
                    FieldKey.TIME,
                    defaultErrorMapper.map(AddTravelFieldsValidationErrors.EMPTY_TIME)
                )
            }
            if (timeBeforeRemind == null) {
                put(
                    FieldKey.TIME_BEFORE_REMIND,
                    defaultErrorMapper.map(AddTravelFieldsValidationErrors.EMPTY_TIME_BEFORE_REMIND)
                )
            }
        }

        if (errors.isNotEmpty()) {
            _fieldErrors.value = errors
            return null
        }
        val id = if (_editedTravelId.value != null) _editedTravelId.value!! else "id+$name"
        return Travel(
            id = id,
            title = name,
            arrivalTime = time!!,
            date = date!!,
            startPlace = startPlace!!,
            destinationPlace = destinationPlace!!,
            timeBeforeRemind = timeBeforeRemind!!,
            transportationMode = mode,
            route = route!!
        )
    }


    fun setPointSelectorAsOrigin() {
        isOriginPoint = true
    }

    fun setPointSelectorAsDestination() {
        isOriginPoint = false
    }

    fun setPlaceSuggestionsQuery(isOriginPlace: Boolean) {
        if (isOriginPlace) {
            _placeSuggestionsQuery.value = _selectedPoints.value.first?.title ?: ""
        } else _placeSuggestionsQuery.value = _selectedPoints.value.second?.title ?: ""
        getPlaceSuggestions()
    }

    fun onReverseButtonPressed() {
        isReversed = !isReversed
        _selectedPoints.value = Pair(_selectedPoints.value.second, _selectedPoints.value.first)
        getRoute()
    }

    fun onPlaceSuggestionsQueryTextChanged(text: String) {
        _placeSuggestionsQuery.value = text
        getPlaceSuggestions()
    }


    fun onClearQueryButtonPressed() {
        _placeSuggestionsQuery.value = ""
        _suggestions.value = emptyList()
    }

    fun onTransportationModeChanged(mode: TransportationMode) {
        _transportationMode.value = mode
        getRoute()
    }

    fun onMapClicked(point: Point) {
        getNearbyPlacesUseCase(point).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    setPlaces(result.data, isOriginPoint)
                    getRoute()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRoute() {
        if (selectedPoints.value.first != null && selectedPoints.value.second != null) {
            getNavigationRouteUseCase(
                origin = selectedPoints.value.first!!.point,
                destination = selectedPoints.value.second!!.point,
                mode = _transportationMode.value
            ).onEach { routeResult ->
                when (routeResult) {
                    is Resource.Error -> _route.value = null
                    is Resource.Loading -> _route.value = null
                    is Resource.Success -> _route.value = routeResult.data
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPlaceSuggestions() {
        getPlaceSuggestionUseCase.invoke(_placeSuggestionsQuery.value).onEach { result ->
            when (result) {
                is Resource.Error -> _suggestions.value = emptyList()
                is Resource.Loading -> _suggestions.value = emptyList()
                is Resource.Success -> _suggestions.value = result.data
            }
        }.launchIn(viewModelScope)
    }

    fun getPlaceCoordinates(placeSuggestion: PlaceSuggestion, isOriginPlace: Boolean) {
        getPlaceCoordinatesUseCase.invoke(placeSuggestion).onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> setPlaces(result.data, isOriginPlace)
            }
        }.launchIn(viewModelScope)
    }

    private fun setPlaces(place: Place?, isOriginPlace: Boolean) =
        if (isOriginPlace) _selectedPoints.value =
            Pair(place, _selectedPoints.value.second)
        else _selectedPoints.value =
            Pair(_selectedPoints.value.first, place)

    sealed interface AddTravelState {
        object Loading : AddTravelState
        object Idle : AddTravelState
        object Success : AddTravelState
        data class Error(val error: CaughtError) : AddTravelState
    }
}