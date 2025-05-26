package ru.dreamteam.travelreminder.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase
import ru.dreamteam.travelreminder.domen.use_cases.LogOutUseCase

class MainActivityViewModel(
    private val checkFirstLaunch: CheckFirstLaunchUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _isFirstLaunch = mutableStateOf(true)
    val isFirstLaunch: State<Boolean> = _isFirstLaunch

    init {
        _isFirstLaunch.value = checkFirstLaunch()
    }

    fun logOut(){
        logOutUseCase()
    }
}