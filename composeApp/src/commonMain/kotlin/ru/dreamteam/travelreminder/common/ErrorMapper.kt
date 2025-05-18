package ru.dreamteam.travelreminder.common

interface ErrorMapper {
    fun map(throwable: Throwable): CaughtError
}

interface CaughtError