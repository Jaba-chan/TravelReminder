package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetNearbyPlacesUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(point: Point) = flow {
        emit(Resource.Loading())
        try {
            val list = mapRepository.getNearbyPlaces(point)
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something error!"))
        }
    }
}