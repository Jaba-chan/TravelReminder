package ru.dreamteam.travelreminder.presentation.sing_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.use_cases.FillTableUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase


class SingInViewModel(
    private val signInUseCase: SignInByEmailAndPasswordUseCase,
    private val fillTableUseCase: FillTableUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Idle)
    val state = _state.asStateFlow()

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun onSignInButtonPressed() {
        singIn(
            SignInByEmailAndPasswordParams(
                email = email.value,
                password = password.value
            )
        )
    }

    fun onEmailTextChanged(newText: String) {
        _email.value = newText
            .filter { it.code < 128 }
    }

    fun onPasswordTextChanged(newText: String) {
        _password.value = newText
    }

    fun resetStateToIdle(){
        _state.value = SignInState.Idle
    }

    fun resetFields(){
        _email.value = ""
        _password.value = ""
    }

    private fun singIn(params: SignInByEmailAndPasswordParams) {
        signInUseCase(params).onEach { result ->
            when (result) {
                is Resource.Error -> _state.value = SignInState.Error(result.error)
                is Resource.Loading -> _state.value = SignInState.Loading
                is Resource.Success -> {
                    _state.value = SignInState.Success
                    fillTableUseCase()
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed interface SignInState {
        object Loading : SignInState
        object Idle : SignInState
        object Success : SignInState
        data class Error(val error: CaughtError) : SignInState
    }

}