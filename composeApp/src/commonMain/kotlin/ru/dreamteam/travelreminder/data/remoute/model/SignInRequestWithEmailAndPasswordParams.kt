package ru.dreamteam.travelreminder.data.remoute.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestWithEmailAndPasswordParams(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)
