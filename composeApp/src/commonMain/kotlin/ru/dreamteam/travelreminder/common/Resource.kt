package ru.dreamteam.travelreminder.common

sealed class Resource<out T>{
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: CaughtError, val message: String?) :  Resource<T>()
    class Loading<T> : Resource<T>()
}