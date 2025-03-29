package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.data.remoute.model.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.dreamteam.travelreminder.domen.model.SignInWithEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.use_cases.SignInWithEmailAndPasswordUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.repository.TravelRepository


class TravelsViewModel(private val repository: TravelRepository,
) : ViewModel() {

    private val _travels = MutableStateFlow<List<Travel>>(emptyList())
    val travels: StateFlow<List<Travel>> = _travels

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