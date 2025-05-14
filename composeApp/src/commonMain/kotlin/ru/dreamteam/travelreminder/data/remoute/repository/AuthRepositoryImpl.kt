package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.FirebaseApiKey
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.mapper.params.toRequest
import ru.dreamteam.travelreminder.data.mapper.params.toSignInRequest
import ru.dreamteam.travelreminder.domen.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.domen.model.error_response.ErrorResponse
import ru.dreamteam.travelreminder.domen.model.ResponseResult
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.response.RefreshTokenResponse
import ru.dreamteam.travelreminder.domen.model.response.SignInResponse
import ru.dreamteam.travelreminder.domen.model.response.SignUpResponse
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

private const val ERROR_FIELD = "error"

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val storage: UserUidStorage
) : AuthRepository {

    private val apiKey = FirebaseApiKey.getApiKey()
    private val singInBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
    private val singUpBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
    private val changePasswordBaseUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:update?key=$apiKey"

    override fun isFirstLaunch(): Boolean {
        return storage.getUserUid() == null
    }

    override suspend fun refreshToken(refreshToken: String?) {
        val refreshUrl = "https://securetoken.googleapis.com/v1/token?key=$apiKey"
        val stringResponse: String = client.post(refreshUrl) {
            contentType(ContentType.Application.Json)
            setBody("refresh_token" to storage.getRefreshToken())
        }.body()

        val response = Json.decodeFromString<RefreshTokenResponse>(stringResponse)
        println("idToken!!!!!!!!!!!!!!!!!!!!!" + response.idToken)
        storage.setIdToken(response.idToken)
    }

    override suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams): ResponseResult<SignInResponse> {
        val stringResponse: String = client.post(singInBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toRequest(returnSecureToken = true))
        }.body()

        val response = if (ERROR_FIELD in stringResponse) {
            val error = Json.decodeFromString<ErrorResponse>(stringResponse)
            ResponseResult.Failure(error)
        } else {
            val success = Json.decodeFromString<SignInResponse>(stringResponse)
            saveInLocalStorage(
                success.localId,
                success.idToken,
                success.refreshToken
            )
            ResponseResult.Success(success)
        }
        return response
    }

    override suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams): ResponseResult<SignUpResponse> {
        val stringResponse: String = client.post(singUpBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toRequest(returnSecureToken = true))
        }.body()

        val response = if (ERROR_FIELD in stringResponse) {
            val error = Json.decodeFromString<ErrorResponse>(stringResponse)
            ResponseResult.Failure(error)
        } else {
            val success = Json.decodeFromString<SignUpResponse>(stringResponse)
            saveInLocalStorage(
                success.localId,
                success.idToken,
                success.refreshToken
            )
            ResponseResult.Success(success)
        }
        return response
    }

    override suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam): ResponseResult<ChangePasswordByEmailResponse> {
        val stringResponse: String = client.post(singInBaseUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toSignInRequest(returnSecureToken = true))
        }.body()

        return if (ERROR_FIELD in stringResponse) {
            val signInError = Json.decodeFromString<ErrorResponse>(stringResponse)
            ResponseResult.Failure(signInError)
        } else {
            val signInSuccess = Json.decodeFromString<SignUpResponse>(stringResponse)
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

            if (ERROR_FIELD in stringResponseChangePassword) {
                println(stringResponseChangePassword)
                val changePasswordError =
                    Json.decodeFromString<ErrorResponse>(stringResponseChangePassword)
                ResponseResult.Failure(changePasswordError)
            } else {

                val changePasswordSuccess =
                    Json.decodeFromString<ChangePasswordByEmailResponse>(
                        stringResponseChangePassword
                    )
                saveInLocalStorage(
                    changePasswordSuccess.localId,
                    changePasswordSuccess.idToken,
                    changePasswordSuccess.refreshToken
                )
                ResponseResult.Success(changePasswordSuccess)
            }
        }
    }

    private fun saveInLocalStorage(localId: String?, idToken: String?, refreshToken: String?) {
        storage.setUserUid(localId)
        storage.setIdToken(idToken)
        storage.setRefreshToken(refreshToken)
    }
}