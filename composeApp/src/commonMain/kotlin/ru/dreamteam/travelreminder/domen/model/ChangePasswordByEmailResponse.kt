package ru.dreamteam.travelreminder.domen.model


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

data class ChangePasswordByEmailResponse(
    val email: String?
)