package ru.dreamteam.travelreminder.common

interface ErrorMapper {
    fun map(throwable: Throwable): CaughtError
    fun <T : Enum<T>> map(error: Enum<T>): CaughtError
}

interface CaughtError