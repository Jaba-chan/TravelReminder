package ru.dreamteam.travelreminder.domen.model

data class ChangePasswordByEmailParam(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)
