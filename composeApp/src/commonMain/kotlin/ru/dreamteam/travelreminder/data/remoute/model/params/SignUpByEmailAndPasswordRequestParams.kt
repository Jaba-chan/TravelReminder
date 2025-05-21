package ru.dreamteam.travelreminder.data.remoute.model.params

import kotlinx.serialization.Serializable

@Serializable
data class SignUpByEmailAndPasswordRequestParams(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)
