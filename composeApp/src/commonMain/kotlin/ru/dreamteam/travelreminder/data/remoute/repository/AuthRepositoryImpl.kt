package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.mapper.params.toRequest
import ru.dreamteam.travelreminder.data.mapper.params.toSignInRequest
import ru.dreamteam.travelreminder.data.remoute.model.FirebaseAuthException
import ru.dreamteam.travelreminder.data.remoute.model.response.ChangePasswordByEmailResponse
import ru.dreamteam.travelreminder.data.remoute.model.response.RefreshTokenResponse
import ru.dreamteam.travelreminder.data.remoute.model.response.SignInResponse
import ru.dreamteam.travelreminder.data.remoute.model.response.SignUpResponse
import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponse
import ru.dreamteam.travelreminder.domen.model.params.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.params.SignInByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.params.SignUpByEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val storage: UserUidStorage,
    apiKey: String
) : AuthRepository {

    private val signInUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
    private val signUpUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
    private val changePasswordUrl =
        "https://identitytoolkit.googleapis.com/v1/accounts:update?key=$apiKey"
    private val refreshTokenUrl = "https://securetoken.googleapis.com/v1/token?key=$apiKey"

    override fun isFirstLaunch(): Boolean =
        storage.getUserUid() == null

    override suspend fun logOut() {
        storage.clear()
    }

    override suspend fun refreshToken(refreshToken: String?) {
        val body = mapOf("refresh_token" to (storage.getRefreshToken() ?: ""))
        val raw = client.post(refreshTokenUrl) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<String>()

        if (ERROR_FIELD in raw) {
            val err = Json.decodeFromString<ErrorResponse>(raw)
            throw FirebaseAuthException(err)
        }

        val resp = Json.decodeFromString<RefreshTokenResponse>(raw)
        storage.setIdToken(resp.idToken)
    }

    override suspend fun signInByEmailAndPassword(params: SignInByEmailAndPasswordParams) {
        performRequest<SignInResponse>(
            signInUrl,
            params.toRequest(returnSecureToken = true)
        ) { resp ->
            saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
        }
    }

    override suspend fun signUpByEmailAndPassword(params: SignUpByEmailAndPasswordParams) {
        performRequest<SignUpResponse>(
            signUpUrl,
            params.toRequest(returnSecureToken = true)
        ) { resp ->
            saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
        }
    }

    override suspend fun changePasswordByEmail(params: ChangePasswordByEmailParam) {
        val rawSignIn = client.post(signInUrl) {
            contentType(ContentType.Application.Json)
            setBody(params.toSignInRequest(returnSecureToken = true))
        }.body<String>()

        if (ERROR_FIELD in rawSignIn) {
            val err = Json.decodeFromString<ErrorResponse>(rawSignIn)
            throw FirebaseAuthException(err)
        }
        val signInResp = Json.decodeFromString<SignInResponse>(rawSignIn)
        saveInLocalStorage(signInResp.localId, signInResp.idToken, signInResp.refreshToken)

        performRequest<ChangePasswordByEmailResponse>(
            url = changePasswordUrl,
            body = params.toRequest(
                idToken = signInResp.idToken ?: "",
                returnSecureToken = true
            )
        ) { resp ->
            saveInLocalStorage(resp.localId, resp.idToken, resp.refreshToken)
        }
    }

    private suspend inline fun <reified T : Any> performRequest(
        url: String,
        body: Any,
        crossinline onSuccess: (T) -> Unit
    ) {
        val raw = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<String>()

        if (ERROR_FIELD in raw) {
            val err = Json.decodeFromString<ErrorResponse>(raw)
            throw FirebaseAuthException(err)
        }

        val success = Json.decodeFromString<T>(raw)
        onSuccess(success)
    }

    private fun saveInLocalStorage(localId: String?, idToken: String?, refreshToken: String?) {
        storage.setUserUid(localId)
        storage.setIdToken(idToken)
        storage.setRefreshToken(refreshToken)
    }

    private companion object {
        const val ERROR_FIELD = "error"
    }
}
