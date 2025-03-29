package ru.dreamteam.travelreminder.domen.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dreamteam.travelreminder.common.Resource
import ru.dreamteam.travelreminder.data.mapper.toDomain
import ru.dreamteam.travelreminder.domen.model.SignInResponse
import ru.dreamteam.travelreminder.domen.model.SignInWithEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import io.ktor.client.plugins.*

class SignInWithEmailAndPasswordUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(params: SignInWithEmailAndPasswordParams): Flow<Resource<SignInResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.singInByEmailAndPassword(params)
            emit(Resource.Success(response.toDomain()))
        } catch (e: ClientRequestException){
            emit(Resource.Error("Ди нахуй!"))
        }
    }
}