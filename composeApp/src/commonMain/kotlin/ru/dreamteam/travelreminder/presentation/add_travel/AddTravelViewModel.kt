package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Date
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.Time
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.presentation.CaughtErrorImpl
import ru.dreamteam.travelreminder.presentation.DefaultErrorMapper
import ru.dreamteam.travelreminder.presentation.show_map.SharedPlaceFlow
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel.SignInState

class AddTravelViewModel(
    private val addTravelUseCase: AddTravelUseCase,
    private val defaultErrorMapper: DefaultErrorMapper,
    private val sharedPlaceFlow: SharedPlaceFlow,
) : ViewModel() {

    init {
        viewModelScope.launch {
            sharedPlaceFlow.places.collect { event ->
                _selectedPoints.value = event
            }
        }

        viewModelScope.launch {
            sharedPlaceFlow.mode.collect { mode ->
                _transportationMode.value = mode
            }
        }
    }

    private val _state = MutableStateFlow<AddTravelState>(AddTravelState.Idle)
    val state = _state.asStateFlow()

    private val _isDatePickerVisible = mutableStateOf(false)
    val isDatePickerVisible: State<Boolean> = _isDatePickerVisible

    private val _isTimePickerVisible = mutableStateOf(false)
    val isTimePickerVisible: State<Boolean> = _isTimePickerVisible

    private val _travelDate = mutableStateOf<Date?>(null)
    val travelDate: State<Date?> = _travelDate

    private val _travelTime = mutableStateOf<Time?>(null)
    val travelTime: State<Time?> = _travelTime

    private val _timeBeforeRemind = mutableStateOf<Time?>(null)
    val timeBeforeRemind: State<Time?> = _timeBeforeRemind

    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName

    private val _transportationMode = mutableStateOf(TransportationMode.DRIVE)
    val transportationMode: State<TransportationMode> = _transportationMode

    private var _selectedPoints = mutableStateOf<Pair<Place?, Place?>>(Pair(null, null))
    val selectedPoints: State<Pair<Place?, Place?>> = _selectedPoints

    fun addTravel() {
        validate().let {
            if (it != null)
                addTravelUseCase.invoke(it)
                    .onEach { resource ->
                        _state.value = when (resource) {
                            is Resource.Error -> AddTravelState.ErrorFromServer(resource.error)
                            is Resource.Loading -> AddTravelState.Loading
                            is Resource.Success -> AddTravelState.Success.also {
                                _state.value = AddTravelState.Idle
                            }
                        }
                    }.launchIn(viewModelScope)

        }
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

    fun hideTimePicker() {
        _isTimePickerVisible.value = false
    }

    fun setTravelDate(date: Date?) {
        _travelDate.value = date
    }

    fun setTravelTime(time: Time) {
        _travelTime.value = time
    }

    fun setTimeBeforeRemind(time: Time) {
        _travelTime.value = time
    }

    fun onTravelNameTextChanged(newText: String) {
        _travelName.value = newText
    }

    private fun validate(): Travel? {
        val name = _travelName.value.trim()
        val time = _travelTime.value
        val timeBeforeRemind = _timeBeforeRemind.value
        val date = _travelDate.value
        val destination = _selectedPoints.value.second
        val mode = _transportationMode.value

        if (name.isEmpty()) {
            _state.value =
                AddTravelState.ValidationError(
                    defaultErrorMapper.map(
                        AddTravelFieldsValidationErrors.EMPTY_NAME
                    )
                )
            return null
        }
        if (destination == null) {
            _state.value =
                AddTravelState.ValidationError(
                    defaultErrorMapper.map(
                        AddTravelFieldsValidationErrors.EMPTY_ROUTE
                    )
                )
            return null
        }
        if (date == null) {
            _state.value =
                AddTravelState.ValidationError(
                    defaultErrorMapper.map(
                        AddTravelFieldsValidationErrors.EMPTY_DATE
                    )
                )
            return null
        }
        if (time == null) {
            _state.value =
                AddTravelState.ValidationError(
                    defaultErrorMapper.map(
                        AddTravelFieldsValidationErrors.EMPTY_TIME
                    )
                )
            return null
        }
        if (timeBeforeRemind == null) {
            _state.value =
                AddTravelState.ValidationError(
                    defaultErrorMapper.map(
                        AddTravelFieldsValidationErrors.EMPTY_TIME_BEFORE_REMIND
                    )
                )
            return null
        }
        return Travel(
            id = "id$name",
            title = name,
            arrivalTime = time,
            date = date,
            destinationPlace = destination,
            timeBeforeRemind = timeBeforeRemind,
            transportationMode = mode
        )
    }

    sealed interface AddTravelState {
        object Loading : AddTravelState
        object Idle : AddTravelState
        object Success : AddTravelState
        data class ErrorFromServer(val error: CaughtError) : AddTravelState
        data class ValidationError(val error: CaughtError) : AddTravelState
    }
}