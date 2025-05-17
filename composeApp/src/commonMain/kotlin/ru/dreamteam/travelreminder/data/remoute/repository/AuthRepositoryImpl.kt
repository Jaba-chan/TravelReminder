package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.mapper.params.toRequest
import ru.dreamteam.travelreminder.data.mapper.params.toSignInRequest
import ru.dreamteam.travelreminder.domen.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.domen.model.error_response.ErrorResponse
import ru.dreamteam.travelreminder.data.local.model.map.ResponseResult
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
    private val storage: UserUidStorage,
    apiKey: String
) : AuthRepository {

    private val signInUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
    private val signUpUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
    private val changePasswordUrl = "https://identitytoolkit.googleapis.com/v1/accounts:update?key=$apiKey"
    private val refreshTokenUrl = "https://securetoken.googleapis.com/v1/token?key=$apiKey"

    override fun isFirstLaunch(): Boolean =
        storage.getUserUid() == null

    override suspend fun refreshToken(refreshToken: String?) {
        val body = mapOf("refresh_token" to (storage.getRefreshToken() ?: ""))
        val responseText = client.post(refreshTokenUrl) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<String>()

        val response = Json.decodeFromString<RefreshTokenResponse>(responseText)
        storage.setIdToken(response.idToken)
    }

    override suspend fun signInByEmailAndPassword(
        params: SignInByEmailAndPasswordParams
    ): ResponseResult<SignInResponse> =
        performRequest(
            url = signInUrl,
            body = params.toRequest(returnSecureToken = true),
            onSuccess = { resp ->
                saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
                ResponseResult.Success(resp)
            }
        )

    override suspend fun signUpByEmailAndPassword(
        params: SignUpByEmailAndPasswordParams
    ): ResponseResult<SignUpResponse> =
        performRequest(
            url = signUpUrl,
            body = params.toRequest(returnSecureToken = true),
            onSuccess = { resp ->
                saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
                ResponseResult.Success(resp)
            }
        )

    override suspend fun changePasswordByEmail(
        params: ChangePasswordByEmailParam
    ): ResponseResult<ChangePasswordByEmailResponse> {
        val signInRaw = client.post(signInUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toSignInRequest(returnSecureToken = true))
        }.body<String>()

        if (ERROR_FIELD in signInRaw) {
            val error = Json.decodeFromString<ErrorResponse>(signInRaw)
            return ResponseResult.Failure(error)
        }
        val signInSuccess = Json.decodeFromString<SignInResponse>(signInRaw)
        saveInLocalStorage(
            signInSuccess.localId,
            signInSuccess.idToken,
            signInSuccess.refreshToken
        )

        return performRequest(
            url = changePasswordUrl,
            body = params.toRequest(
                idToken = signInSuccess.idToken ?: "",
                returnSecureToken = true
            )
        ) { resp ->
            saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
            ResponseResult.Success(resp)
        }
    }

    private suspend inline fun <reified T : Any> performRequest(
        url: String,
        body: Any,
        crossinline onSuccess: (T) -> ResponseResult<T>
    ): ResponseResult<T> {
        val raw = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<String>()

        return if (ERROR_FIELD in raw) {
            val error = Json.decodeFromString<ErrorResponse>(raw)
            ResponseResult.Failure(error)
        } else {
            val success = Json.decodeFromString<T>(raw)
            onSuccess(success)
        }
    }

    private fun saveInLocalStorage(
        localId: String?,
        idToken: String?,
        refreshToken: String?
    ) {
        storage.setUserUid(localId)
        storage.setIdToken(idToken)
        storage.setRefreshToken(refreshToken)
    }
}