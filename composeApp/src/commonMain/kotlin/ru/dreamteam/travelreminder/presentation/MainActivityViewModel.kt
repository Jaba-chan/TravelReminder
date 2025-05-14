package ru.dreamteam.travelreminder.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase

class MainActivityViewModel(
    private val checkFirstLaunch: CheckFirstLaunchUseCase,
) : ViewModel() {

    private val _isFirstLaunch = mutableStateOf(true)
    val isFirstLaunch: State<Boolean> = _isFirstLaunch

    init {
        _isFirstLaunch.value = checkFirstLaunch()
    }

}