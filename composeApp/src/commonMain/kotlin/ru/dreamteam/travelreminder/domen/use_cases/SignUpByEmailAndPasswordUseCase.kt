package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.remoute.model.SignUpResultDto
import ru.dreamteam.travelreminder.domen.model.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.SignUpResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class SignUpByEmailAndPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: SignUpByEmailAndPasswordParams): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val response = authRepository.signUpByEmailAndPassword(params)){
                is SignUpResultDto.Failure -> emit(Resource.Error(message = response.error.error.message))
                is SignUpResultDto.Success -> emit(Resource.Success(data = response.response.toDomain()))
            }
        } catch (e: ClientRequestException){
            emit(Resource.Error("!!!"))
        }
    }
}