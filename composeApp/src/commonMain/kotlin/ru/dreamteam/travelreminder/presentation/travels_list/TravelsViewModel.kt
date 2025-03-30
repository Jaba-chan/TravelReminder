package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.data.remoute.model.travel.TravelDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.dreamteam.travelreminder.domen.repository.TravelRepository


class TravelsViewModel(private val repository: TravelRepository
) : ViewModel() {

    private val _travels = MutableStateFlow<List<TravelDto>>(emptyList())
    val travels: StateFlow<List<TravelDto>> = _travels

    fun loadTravels() {
        viewModelScope.launch {
                val travelsList = repository.getTravels()
                _travels.value = travelsList
        }
    }

    fun onButtonPressed(){
        loadTravels()
    }

}