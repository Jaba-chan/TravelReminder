package ru.dreamteam.travelreminder.data.remoute.model.params

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordByEmailRequestParam (
    val idToken: String,
    val password: String,
    val returnSecureToken: Boolean
)