package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class GetTravelsUseCase(
    private val localTravelRepository: TravelRepository,
    private val remoteTravelRepository: TravelRepository,
    private val networkObserver: NetworkConnectivityObserver,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(): Flow<Resource<List<Travel>>> =
        flow {
            emit(Resource.Loading())
            var travels = localTravelRepository.getTravels()
            if (networkObserver.isConnected()){
                travels = remoteTravelRepository.getTravels()
            }
            emit(Resource.Success(data = travels))
        }.catch { e ->
            println(e.message)
            val error = errorMapper.map(e)
            emit(Resource.Error(error = error, message = e.message))
        }
}