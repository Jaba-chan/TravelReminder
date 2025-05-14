package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class GetTravelsUseCase(private val travelRepository: TravelRepository) {
    operator fun invoke(): Flow<Resource<List<Travel>>> = flow {
        try {
            emit(Resource.Loading())
            val travels = travelRepository.getTravels()
            emit(Resource.Success(data = travels))
        } catch (e: ClientRequestException) {
            emit(Resource.Error("!!!"))
        }
    }
}