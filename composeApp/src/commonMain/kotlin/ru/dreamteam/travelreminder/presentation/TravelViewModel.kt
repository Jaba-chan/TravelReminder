package ru.dreamteam.travelreminder.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.model.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TravelViewModel(private val repository: TravelRepositoryImpl) : ViewModel() {
    private val _travels = MutableStateFlow<List<Travel>>(emptyList())
    val travels: StateFlow<List<Travel>> = _travels

    init {
        this.loadTravels()

    }
    fun loadTravels() {
        viewModelScope.launch {
                val travelsList = repository.getTravels()
                _travels.value = travelsList
        }
    }
}