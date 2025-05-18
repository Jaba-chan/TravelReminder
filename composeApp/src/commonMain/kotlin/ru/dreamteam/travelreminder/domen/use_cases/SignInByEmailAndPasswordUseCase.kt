package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class SignInByEmailAndPasswordUseCase(
    private val authRepository: AuthRepository,
    private val errorMapper: ErrorMapper
) {
    operator fun invoke(params: SignInByEmailAndPasswordParams): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            authRepository.signInByEmailAndPassword(params)
            emit(Resource.Success(Unit))
        }.catch { e ->
            val error = errorMapper.map(e)
            emit(Resource.Error(error = error, message = e.message))
        }
}