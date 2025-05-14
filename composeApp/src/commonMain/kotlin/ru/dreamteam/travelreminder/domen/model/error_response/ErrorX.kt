package ru.dreamteam.travelreminder.domen.model.error_response


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ErrorX(
    @SerialName("domain")
    val domain: String? = null,
    @SerialName("message")
    val message: String?,
    @SerialName("reason")
    val reason: String?
)