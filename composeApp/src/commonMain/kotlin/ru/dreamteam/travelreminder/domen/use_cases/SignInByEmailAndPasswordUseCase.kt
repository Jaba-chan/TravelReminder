package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.domen.model.SignInResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import io.ktor.client.plugins.*
import ru.dreamteam.travelreminder.data.remoute.model.SignInResultDto
import ru.dreamteam.travelreminder.domen.model.SignInByEmailAndPasswordParams

class SignInByEmailAndPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: SignInByEmailAndPasswordParams): Flow<Resource<SignInResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                when (val response = authRepository.signInByEmailAndPassword(params)){
                    is SignInResultDto.Failure -> emit(Resource.Error(message = response.error.error.message))
                    is SignInResultDto.Success -> emit(Resource.Success(data = response.response.toDomain()))
                }
            } catch (e: ClientRequestException) {
                emit(Resource.Error("Пользователя нет!"))
            }
        }
}