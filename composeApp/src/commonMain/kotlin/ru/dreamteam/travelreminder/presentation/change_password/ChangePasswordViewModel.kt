package ru.dreamteam.travelreminder.presentation.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase
import ru.dreamteam.travelreminder.presentation.CaughtErrorImpl

class ChangePasswordViewModel(
    private val changePasswordUseCase: ChangePasswordByEmailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ChangePasswordState>(ChangePasswordState.Loading)
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
                is Resource.Error -> _state.value = ChangePasswordState.Error(result.error)
                is Resource.Loading -> _state.value = ChangePasswordState.Loading
                is Resource.Success -> _state.value = ChangePasswordState.Success
            }
        }.launchIn(viewModelScope)
    }

    sealed interface ChangePasswordState {
        object Loading : ChangePasswordState
        object Success : ChangePasswordState
        data class Error(val error: CaughtError) : ChangePasswordState
    }

}