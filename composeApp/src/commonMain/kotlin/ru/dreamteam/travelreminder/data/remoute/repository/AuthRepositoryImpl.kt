package ru.dreamteam.travelreminder.data.remoute.repository

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.dreamteam.travelreminder.data.local.storage.FirebaseApiKeyProvider
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.remoute.KtorClient.client
import ru.dreamteam.travelreminder.data.remoute.model.SignInResponseDao
import ru.dreamteam.travelreminder.data.remoute.model.SignInRequestWithEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.model.SignInWithEmailAndPasswordParams
import ru.dreamteam.travelreminder.domen.repository.AuthRepository

class AuthRepositoryImpl(private val storage: UserUidStorage): AuthRepository {
    private val baseUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${FirebaseApiKeyProvider.getApiKey()}"

    override suspend fun singInByEmailAndPassword(params: SignInWithEmailAndPasswordParams): SignInResponseDao {
        val request = SignInRequestWithEmailAndPasswordParams(
            email = params.email,
            password = params.password,
            returnSecureToken = true
        )

        val response: SignInResponseDao = client.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        storage.setUserUid(response.localId)

        return response
    }
}