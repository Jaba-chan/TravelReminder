package ru.dreamteam.travelreminder.data.remoute.model.response


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ChangePasswordByEmailResponse(
    @SerialName("email")
    val email: String?,
    @SerialName("expiresIn")
    val expiresIn: String? = null,
    @SerialName("idToken")
    val idToken: String? = null,
    @SerialName("localId")
    val localId: String?,
    @SerialName("passwordHash")
    val passwordHash: String?,
    @SerialName("refreshToken")
    val refreshToken: String? = null
)