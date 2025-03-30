package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailResultDto
import ru.dreamteam.travelreminder.data.remoute.model.SignUpResultDto
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.domen.model.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.SignUpResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class ChangePasswordByEmailUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: ChangePasswordByEmailParam): Flow<Resource<ChangePasswordByEmailResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val response = authRepository.changePasswordByEmail(params)){
                is ChangePasswordByEmailResultDto.Failure -> emit(Resource.Error(message = response.error.error.message))
                is ChangePasswordByEmailResultDto.Success -> emit(Resource.Success(data = response.response.toDomain()))
            }
        } catch (e: ClientRequestException){
            emit(Resource.Error("!!!"))
        }
    }
}