package ru.dreamteam.travelreminder.presentation.sing_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.SignInByEmailAndPasswordParams


class SingInViewModel(private val signInUseCase: SignInByEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun onSignInButtonPressed(email: String, password: String){
        singIn(SignInByEmailAndPasswordParams(email = email, password = password))
    }

    private fun singIn(params: SignInByEmailAndPasswordParams) {
        signInUseCase(params).onEach { result ->
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