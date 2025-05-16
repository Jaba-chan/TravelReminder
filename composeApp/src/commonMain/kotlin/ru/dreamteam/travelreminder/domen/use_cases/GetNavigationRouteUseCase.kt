package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetNavigationRouteUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(
        origin: Point,
        destination: Point,
        mode: TransportationMode
    ) = flow {
        emit(Resource.Loading())
        try {
            val route =
                mapRepository.buildRoute(origin = origin, destination = destination, mode = mode)
            emit(Resource.Success(route))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something error!"))
        }
    }
}