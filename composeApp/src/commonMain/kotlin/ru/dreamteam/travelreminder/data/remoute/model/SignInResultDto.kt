package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable
import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponseDto

sealed class SignInResultDto {
    @Serializable
    data class Success(val response: SignInResponseDto) : SignInResultDto()

    @Serializable
    data class Failure(val error: ErrorResponseDto) : SignInResultDto()
}