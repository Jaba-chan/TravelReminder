package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.local.model.map.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.data.remoute.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class ChangePasswordByEmailUseCase(
    private val authRepository: AuthRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(params: ChangePasswordByEmailParam): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            authRepository.changePasswordByEmail(params)
            emit(Resource.Success(Unit))
        }.catch { e ->
            val error = errorMapper.map(e)
            emit(Resource.Error(error = error, message = e.message))
        }
}