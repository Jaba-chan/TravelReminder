package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class GetTravelByIdUseCase(
    private val localTravelRepository: TravelRepository,
    private val remoteTravelRepository: TravelRepository,
    private val networkObserver: NetworkConnectivityObserver,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(id: String): Flow<Resource<Travel>> =
        flow {
            emit(Resource.Loading())
            val travel = localTravelRepository.getTravelById(id)
            if (networkObserver.isConnected()){
                remoteTravelRepository.getTravelById(id)
            }
            emit(Resource.Success(data = travel))
        }.catch { e ->
            val error = errorMapper.map(e)
            println(e.message)
            emit(Resource.Error(error = error, message = e.message))
        }
}