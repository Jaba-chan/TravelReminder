package ru.dreamteam.travelreminder.domen.model

data class SignInResponse(
    val idToken: String?,
    val email: String?,
    val refreshToken: String?,
    val expiresIn: String?,
    val localId: String?,
    val registered: Boolean?
)