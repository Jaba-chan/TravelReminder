package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.domen.model.response.SignInResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import io.ktor.client.plugins.*
import ru.dreamteam.travelreminder.domen.model.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams

class SignInByEmailAndPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: SignInByEmailAndPasswordParams): Flow<Resource<SignInResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val response = authRepository.signInByEmailAndPassword(params)){
                    is ResponseResult.Failure -> emit(Resource.Error(message = response.error.error.message))
                    is ResponseResult.Success -> emit(Resource.Success(data = response.response))
                }
            } catch (e: ClientRequestException) {
                emit(Resource.Error("Пользователя нет!"))
            }
        }
}