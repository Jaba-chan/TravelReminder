package ru.dreamteam.travelreminder.domen.model.error_response


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Error(
    @SerialName("code")
    val code: Int?,
    @SerialName("errors")
    val errors: List<ErrorX>?,
    @SerialName("message")
    val message: String
)