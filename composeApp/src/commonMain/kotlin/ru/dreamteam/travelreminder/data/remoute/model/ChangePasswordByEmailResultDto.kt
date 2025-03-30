package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable
import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponseDto

sealed class ChangePasswordByEmailResultDto {
    @Serializable
    data class Success(val response: ChangePasswordByEmailResponseDto) : ChangePasswordByEmailResultDto()

    @Serializable
    data class Failure(val error: ErrorResponseDto) : ChangePasswordByEmailResultDto()
}