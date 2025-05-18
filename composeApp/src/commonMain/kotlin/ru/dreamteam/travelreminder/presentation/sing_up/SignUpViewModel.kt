package ru.dreamteam.travelreminder.presentation.sing_up

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
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.use_cases.SignUpByEmailAndPasswordUseCase


class SignUpViewModel(
    private val signUpUseCase: SignUpByEmailAndPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state = _state.asStateFlow()

    private val _email                  = mutableStateOf("")
    val email: State<String>            = _email

    private val _password               = mutableStateOf("")
    val password: State<String>         = _password

    private val _passwordAgain          = mutableStateOf("")
    val passwordAgain: State<String>    = _passwordAgain

    fun onSignUpButtonPressed() {
        singUp(SignUpByEmailAndPasswordParams(
            email = email.value,
            password = password.value)
        )
    }

    fun onEmailTextChanged(newText: String) {
        _email.value = newText
            .filter { it.code < 128 }
    }

    fun onPasswordTextChanged(newText: String) {
        _password.value = newText
    }

    fun onPasswordAgainTextChanged(newText: String) {
        _passwordAgain.value = newText
    }

    private fun singUp(params: SignUpByEmailAndPasswordParams) {
        signUpUseCase(params).onEach { result ->
            when (result) {
                is Resource.Error   -> _state.value = SignUpState.Error(result.error)
                is Resource.Loading -> _state.value = SignUpState.Loading
                is Resource.Success -> _state.value = SignUpState.Success
            }
        }.launchIn(viewModelScope)
    }

    sealed interface SignUpState {
        object Loading : SignUpState
        object Idle    : SignUpState
        object Success : SignUpState
        data class Error(val error: CaughtError) : SignUpState
    }

}