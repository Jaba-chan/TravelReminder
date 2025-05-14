package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDto
import ru.dreamteam.travelreminder.domen.model.response.AddTravelResponse
import ru.dreamteam.travelreminder.domen.model.response.DeleteTravelResponse
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.domen.repository.TravelRepository

class AddTravelUseCase(private val travelRepository: TravelRepository) {
    operator fun invoke (travel: Travel): Flow<Resource<AddTravelResponse>> = flow {
            try {
                travelRepository.addTravel(travel)
                emit(Resource.Loading())
            } catch (e: ClientRequestException) {
                emit(Resource.Error("!!!"))
            }
        }
}