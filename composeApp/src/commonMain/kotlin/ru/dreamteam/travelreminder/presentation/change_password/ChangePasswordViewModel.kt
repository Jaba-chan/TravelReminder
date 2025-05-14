package ru.dreamteam.travelreminder.presentation.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase

class ChangePasswordViewModel(
    private val changePasswordUseCase: ChangePasswordByEmailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun onSignInButtonPressed(email: String, oldPassword: String, newPassword: String) {
        changePassword(
            ChangePasswordByEmailParam(
                email = email,
                oldPassword = oldPassword,
                newPassword = newPassword
            )
        )
    }

    private fun changePassword(params: ChangePasswordByEmailParam) {
        changePasswordUseCase(params).onEach { result ->
            when (result) {
                is Resource.Error -> _state.value = State.Error(result.message ?: "aaa")
                is Resource.Loading -> _state.value = State.Loading
                is Resource.Success -> _state.value = State.Success(result.data?.email.toString())
            }
        }.launchIn(viewModelScope)
    }

    sealed interface State {
        object Loading : State
        data class Success(val data: String) : State
        data class Error(val error: String) : State
    }

}