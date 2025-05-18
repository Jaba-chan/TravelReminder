package ru.dreamteam.travelreminder.data.remoute.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResponseDto(
    val suggestions: List<SuggestionDto> = emptyList()
)

@Serializable
data class SuggestionDto(
    @SerialName("placePrediction")
    val placePrediction: PlacePredictionDto? = null
)

@Serializable
class PlacePredictionDto(
    @SerialName("placeId")
    val placeId: String,
    @SerialName("text")
    val fullText: LocalizedTextDto,
    @SerialName("structuredFormat")
    val structured: StructuredFormatDto
)

@Serializable
data class StructuredFormatDto(
    val mainText: LocalizedTextDto,
    val secondaryText: LocalizedTextDto
)

@Serializable
data class LocalizedTextDto(
    val text: String,
    val matches: List<MatchOffsetDto>? = null
)

@Serializable
data class MatchOffsetDto(
    val endOffset: Int
)