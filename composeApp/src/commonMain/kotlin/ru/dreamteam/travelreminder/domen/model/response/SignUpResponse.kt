package ru.dreamteam.travelreminder.domen.model.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SignUpResponse(
    @SerialName("idToken")
    val idToken: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("refreshToken")
    val refreshToken: String? = null,
    @SerialName("expiresIn")
    val expiresIn: String? = null,
    @SerialName("localId")
    val localId: String? = null
)