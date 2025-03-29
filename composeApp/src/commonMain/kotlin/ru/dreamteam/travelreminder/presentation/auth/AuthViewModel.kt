package ru.dreamteam.travelreminder.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
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


class AuthViewModel(private val repository: TravelRepository,
                    private val authUseCase: SignInWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _travels = MutableStateFlow<List<Travel>>(emptyList())
    val travels: StateFlow<List<Travel>> = _travels

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        loadTravels()

    }
    fun loadTravels() {
        viewModelScope.launch {
                val travelsList = repository.getTravels()
                _travels.value = travelsList
        }
    }

    fun onSignInButtonPressed(email: String, password: String){
        singIn(SignInWithEmailAndPasswordParams(email = email, password = password))
    }

    private fun singIn(params: SignInWithEmailAndPasswordParams) {
        authUseCase(params).onEach { result ->
            when (result) {
                is Resource.Error -> _state.value = State.Error(result.message ?: "aaa")

                is Resource.Loading -> _state.value = State.Loading
                is Resource.Success -> _state.value = State.Success(result.data?.email.toString())
            }
        }.launchIn(viewModelScope)
    }

    sealed interface State{
        object Loading: State
        data class Success(val data: String) : State
        data class Error(val error: String): State
    }

}