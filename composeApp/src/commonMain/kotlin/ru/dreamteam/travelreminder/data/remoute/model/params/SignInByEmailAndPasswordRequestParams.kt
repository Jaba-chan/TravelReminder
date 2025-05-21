package ru.dreamteam.travelreminder.data.remoute.model.params

import kotlinx.serialization.Serializable

@Serializable
data class SignInByEmailAndPasswordRequestParams(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)
