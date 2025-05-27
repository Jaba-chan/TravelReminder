package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.repository.DefaultTravelRepository
import ru.dreamteam.travelreminder.domen.model.travel.Travel

class GetTravelByIdUseCase(
    private val travelRepository: DefaultTravelRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(id: String): Flow<Resource<Travel>> =
        flow {
            emit(Resource.Loading())
            val travel = travelRepository.getTravelById(id)
            emit(Resource.Success(data = travel))
        }.catch { e ->
            val error = errorMapper.map(e)
            println(e.message)
            emit(Resource.Error(error = error, message = e.message))
        }
}