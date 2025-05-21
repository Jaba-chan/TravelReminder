package ru.dreamteam.travelreminder.data.remoute

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.dreamteam.travelreminder.data.local.storage.SecretApiKeys
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.data.remoute.model.response.RefreshTokenResponse

fun provideHttpClient(
    storage: UserUidStorage,
    firebaseApiKey: SecretApiKeys
): HttpClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
    client.plugin(HttpSend).intercept { request ->
        var response = execute(request)
        if (response.response.status == HttpStatusCode.Unauthorized) {
            val body = response.response.bodyAsText()
            if ("Permission denied" in body) {
                val refreshUrl = "https://securetoken.googleapis.com/v1/token?key=${firebaseApiKey.getFirebaseApiKey()}"
                val refreshRequest = HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url.takeFrom(refreshUrl)
                    setBody(FormDataContent(Parameters.build {
                        append("grant_type", "refresh_token")
                        append("refresh_token", storage.getRefreshToken() ?: "")
                    }))
                }

                val retryResponse = execute(refreshRequest)
                val refreshBody = retryResponse.body<RefreshTokenResponse>()
                storage.setIdToken(refreshBody.idToken)

                val retryRequest = request.apply {
                    url.parameters.remove("auth")
                    url.parameters.append("auth", storage.getIdToken() ?: "")
                }
                response = execute(retryRequest)

            }
        }
        response
    }

    return client
}
