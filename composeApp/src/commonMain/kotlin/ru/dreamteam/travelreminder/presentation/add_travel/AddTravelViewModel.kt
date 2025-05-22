package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AddTravelViewModel : ViewModel() {

    private val _travelName = mutableStateOf("")
    val travelName: State<String> = _travelName

    fun onTravelNameTextChanged(newText: String) {
        _travelName.value = newText
    }
}