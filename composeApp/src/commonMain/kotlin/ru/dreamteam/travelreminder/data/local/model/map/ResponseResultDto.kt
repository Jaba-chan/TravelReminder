package ru.dreamteam.travelreminder.data.local.model.map

import ru.dreamteam.travelreminder.domen.model.error_response.ErrorResponse

sealed class ResponseResult<out T> {

    data class Success<out T>(val response: T) : ResponseResult<T>()

    data class Failure(val error: ErrorResponse) : ResponseResult<Nothing>()

}