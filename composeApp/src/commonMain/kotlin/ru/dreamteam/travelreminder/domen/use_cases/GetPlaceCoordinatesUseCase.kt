package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.PlaceSuggestion
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetPlaceCoordinatesUseCase(
    private val mapRepository: MapRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(placeSuggestion: PlaceSuggestion) =
        flow {
            emit(Resource.Loading())
            val list = mapRepository.getPlaceCoordinates(placeSuggestion)
            emit(Resource.Success(list))
        }.catch { e ->
            val error = errorMapper.map(e)
            emit(Resource.Error(error = error, message = e.message))
        }
}