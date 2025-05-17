package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.DeleteTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelsUseCase


class TravelsViewModel(
    private val getTravelsUseCase: GetTravelsUseCase,
    private val addTravelsUseCase: AddTravelUseCase,
    private val deleteTravelUseCase: DeleteTravelUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TravelsState>(TravelsState.Loading)
    val state: StateFlow<TravelsState> = _state

    fun loadTravels() {
        getTravelsUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Error -> TravelsState.Error("Something wrong")
                is Resource.Loading -> _state.value = TravelsState.Loading
                is Resource.Success -> {
                    val data = result.data
                    _state.value = when {
                        data == null -> TravelsState.Error("Something wrong")
                        data.isEmpty() -> TravelsState.Empty
                        else -> TravelsState.Success(data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onTravelDeleted(travel: Travel) {
        deleteTravelUseCase(travel).onEach {
        }.launchIn(viewModelScope)
        val currentState = _state.value
        if (currentState is TravelsState.Success) {
            val updatedList = currentState.data.toMutableList().apply { remove(travel) }
            _state.value = if (updatedList.isEmpty()) {
                TravelsState.Empty
            } else {
                TravelsState.Success(updatedList)
            }
        }
    }

    fun onAddButtonPressed() {
         addTravelsUseCase(
            Travel(
                id = "travel3",
                date = "2025-03-29",
                title = "Встреча с командой",
                destinationByAddress = "123 Main St",
                destinationByPoint = PointDto(
                    latitude = 40.7128,
                    longitude = -74.0060
                ),
                arrivalTime = "12:00 PM",
                transportationMode = TransportationMode.DRIVE,
                timeBeforeRemind = "30m"
            )
        ).onEach { }.launchIn(viewModelScope)
    }


    sealed interface TravelsState {
        class Success(val data: List<Travel>) : TravelsState
        object Loading : TravelsState
        data class Error(val reason: String) : TravelsState
        object Empty : TravelsState
    }

}