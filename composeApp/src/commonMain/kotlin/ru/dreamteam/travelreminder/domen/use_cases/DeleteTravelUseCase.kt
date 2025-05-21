package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDto
import ru.dreamteam.travelreminder.data.remoute.model.response.DeleteTravelResponse
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class DeleteTravelUseCase(
    private val travelRepository: TravelRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke (travel: Travel): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            travelRepository.deleteTravel(travel)
            emit(Resource.Success(Unit))
        }.catch { e ->
            val error = errorMapper.map(e)
            emit(Resource.Error(error = error, message = e.message))
        }
}