package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.repository.MapRepository

class GetNavigationRouteUseCase(
    private val mapRepository: MapRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(
        origin: Point,
        destination: Point,
        mode: TransportationMode
    ) = flow {
        emit(Resource.Loading())
        val route = mapRepository.buildRoute(
            origin = origin,
            destination = destination,
            mode = mode
        )
        emit(Resource.Success(route))
    }.catch { e ->
        val error = errorMapper.map(e)
        emit(Resource.Error(error = error, message = e.message))
    }
}