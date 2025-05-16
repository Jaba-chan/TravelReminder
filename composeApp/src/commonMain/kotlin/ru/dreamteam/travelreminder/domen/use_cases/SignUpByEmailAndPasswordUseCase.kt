package ru.dreamteam.travelreminder.domen.use_cases

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.local.model.map.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.response.SignUpResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class SignUpByEmailAndPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: SignUpByEmailAndPasswordParams): Flow<Resource<SignUpResponse>> = flow {
        try {
            emit(Resource.Loading())
            when (val response = authRepository.signUpByEmailAndPassword(params)){
                is ResponseResult.Failure -> emit(Resource.Error(message = response.error.error.message))
                is ResponseResult.Success -> emit(Resource.Success(data = response.response))
            }
        } catch (e: ClientRequestException){
            emit(Resource.Error("!!!"))
        }
    }
}