package ru.dreamteam.travelreminder.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.sync.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase
import ru.dreamteam.travelreminder.domen.use_cases.LogOutUseCase
import ru.dreamteam.travelreminder.sync.SyncController

class MainActivityViewModel(
    private val checkFirstLaunch: CheckFirstLaunchUseCase,
) : ViewModel() {

    private val _isFirstLaunch = mutableStateOf(true)
    val isFirstLaunch: State<Boolean> = _isFirstLaunch

    init {
        _isFirstLaunch.value = checkFirstLaunch()
    }

}