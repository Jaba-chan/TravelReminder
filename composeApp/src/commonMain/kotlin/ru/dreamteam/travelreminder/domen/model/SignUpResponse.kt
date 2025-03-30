package ru.dreamteam.travelreminder.domen.model

data class SignUpResponse(
    val idToken: String?,
    val email: String?,
    val refreshToken: String?,
    val expiresIn: String?,
    val localId: String?
)