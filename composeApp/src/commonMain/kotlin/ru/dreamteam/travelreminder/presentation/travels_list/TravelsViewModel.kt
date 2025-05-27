package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.remoute.model.travel.PointDto
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.DeleteTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelsUseCase
import ru.dreamteam.travelreminder.domen.use_cases.LogOutUseCase
import ru.dreamteam.travelreminder.sync.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.sync.SyncController


class TravelsViewModel(
    private val getTravelsUseCase: GetTravelsUseCase,
    private val deleteTravelUseCase: DeleteTravelUseCase,
    private val networkConnection: NetworkConnectivityObserver,
    private val syncController: SyncController,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TravelsState>(TravelsState.Loading)
    val state: StateFlow<TravelsState> = _state

    init {
        viewModelScope.launch {
            networkConnection.observe().collect { connected ->
                if (connected)
                    syncController.startSync()
                else
                    syncController.stopSync()
            }
        }
    }

    fun loadTravels() {
        getTravelsUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Error -> TravelsState.Error("Something wrong")
                is Resource.Loading -> _state.value = TravelsState.Loading
                is Resource.Success -> {
                    val data = result.data
                    _state.value = when {
                        data.isEmpty() -> TravelsState.Empty
                        else -> TravelsState.Success(result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onTravelDeleted(travel: Travel) {
        deleteTravelUseCase(travel).onEach {}.launchIn(viewModelScope)
        val currentState = _state.value
        if (currentState is TravelsState.Success) {
            val updatedList = currentState.data
                .toMutableList()
                .apply { remove(travel) }
            _state.value = if (updatedList.isEmpty()) {
                TravelsState.Empty
            } else {
                TravelsState.Success(updatedList)
            }
        }
    }
    fun logOut(){
        viewModelScope.launch {
            logOutUseCase()
        }
    }
    sealed interface TravelsState {
        data class Success(val data: List<Travel>) : TravelsState
        object Loading : TravelsState
        data class Error(val reason: String) : TravelsState
        object Empty : TravelsState
    }

}