package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.FirebaseApiKeyProvider
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.mapper.toRequest
import ru.dreamteam.travelreminder.data.mapper.toSignInRequest
import ru.dreamteam.travelreminder.data.remoute.KtorClient.client
import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailResultDto
import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.SignInResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.SignInResultDto
import ru.dreamteam.travelreminder.data.remoute.model.SignUpResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.SignUpResultDto
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

private const val ERROR_FIELD = "error"

class AuthRepositoryImpl(private val storage: UserUidStorage) : AuthRepository {
    private val apiKey = FirebaseApiKeyProvider.getApiKey()
    private val singInBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
    private val singUpBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
    private val changePasswordBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:update?key=$apiKey"

    override suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams): SignInResultDto {
        val stringResponse: String = client.post(singInBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toRequest(returnSecureToken = true))
        }.body()
        println(stringResponse)

        val response = if (ERROR_FIELD in stringResponse) {
            val error = Json.decodeFromString<ErrorResponseDto>(stringResponse)
            SignInResultDto.Failure(error)
        } else {
            val success = Json.decodeFromString<SignInResponseDto>(stringResponse)
            saveInLocalStorage(
                success.localId,
                success.idToken,
                success.refreshToken
            )
            SignInResultDto.Success(success)
        }
        return response
    }

    override suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams): SignUpResultDto {
        val stringResponse: String = client.post(singUpBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toRequest(returnSecureToken = true))
        }.body()

        val response = if (ERROR_FIELD in stringResponse) {
            val error = Json.decodeFromString<ErrorResponseDto>(stringResponse)
            SignUpResultDto.Failure(error)
        } else {
            val success = Json.decodeFromString<SignUpResponseDto>(stringResponse)
            saveInLocalStorage(
                success.localId,
                success.idToken,
                success.refreshToken
            )
            SignUpResultDto.Success(success)
        }
        return response
    }

    override suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam): ChangePasswordByEmailResultDto {
        val stringResponse: String = client.post(singInBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toSignInRequest(returnSecureToken = true))
        }.body()

        return if (ERROR_FIELD in stringResponse) {
            val signInError = Json.decodeFromString<ErrorResponseDto>(stringResponse)
            ChangePasswordByEmailResultDto.Failure(signInError)
        } else {
            val signInSuccess = Json.decodeFromString<SignUpResponseDto>(stringResponse)
            saveInLocalStorage(
                signInSuccess.localId,
                signInSuccess.idToken,
                signInSuccess.refreshToken
            )
            val stringResponseChangePassword: String = client.post(changePasswordBaseUrl) {
                contentType(ContentType.Application.Json)
                setBody(
                    params.toRequest(
                        idToken = signInSuccess.idToken ?: "",
                        returnSecureToken = true
                    )
                )
            }.body()
            println(
                params.toRequest(
                    idToken = "",
                    returnSecureToken = true
                ).password
            )

            if (ERROR_FIELD in stringResponseChangePassword) {
                println(stringResponseChangePassword)
                val changePasswordError =
                    Json.decodeFromString<ErrorResponseDto>(stringResponseChangePassword)
                ChangePasswordByEmailResultDto.Failure(changePasswordError)
            } else {
                println(stringResponseChangePassword)

                val changePasswordSuccess =
                    Json.decodeFromString<ChangePasswordByEmailResponseDto>(
                        stringResponseChangePassword
                    )
                saveInLocalStorage(
                    changePasswordSuccess.localId,
                    changePasswordSuccess.idToken,
                    changePasswordSuccess.refreshToken
                )
                ChangePasswordByEmailResultDto.Success(changePasswordSuccess)
            }
        }
    }

    private fun saveInLocalStorage(idToken: String?, localId: String?, refreshToken: String?) {
        storage.setUserUid(localId)
        storage.setIdToken(idToken)
        storage.setIdToken(refreshToken)
    }
}