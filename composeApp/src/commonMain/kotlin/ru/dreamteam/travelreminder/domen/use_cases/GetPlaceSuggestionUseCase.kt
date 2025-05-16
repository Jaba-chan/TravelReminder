package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetPlaceSuggestionUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(stringText: String) = flow {
        emit(Resource.Loading())
        try {
            val list = mapRepository.getPlaceSuggestions(stringText)
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something error!"))
        }
    }
}