package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable
import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponseDto

sealed class SignUpResultDto {
    @Serializable
    data class Success(val response: SignUpResponseDto) : SignUpResultDto()

    @Serializable
    data class Failure(val error: ErrorResponseDto) : SignUpResultDto()
}