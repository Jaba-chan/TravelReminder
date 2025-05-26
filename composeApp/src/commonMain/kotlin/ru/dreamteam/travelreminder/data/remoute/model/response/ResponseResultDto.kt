package ru.dreamteam.travelreminder.data.remoute.model.response

import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponse

sealed class ResponseResult<out T> {

    data class Success<out T>(val response: T) : ResponseResult<T>()

    data class Failure(val error: ErrorResponse) : ResponseResult<Nothing>()

}