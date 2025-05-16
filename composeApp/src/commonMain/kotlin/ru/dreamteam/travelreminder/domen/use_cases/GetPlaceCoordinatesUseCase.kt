package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetPlaceCoordinatesUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(placeSuggestion: PlaceSuggestion) = flow {
        emit(Resource.Loading())
        try {
            val list = mapRepository.getPlaceCoordinates(placeSuggestion)
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something error!"))
        }
    }
}