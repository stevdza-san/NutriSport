package com.nutrisport.checkout.domain

import com.nutrisport.shared.Constants.PAYPAL_AUTH_ENDPOINT
import com.nutrisport.shared.Constants.PAYPAL_AUTH_KEY
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.encodeBase64
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class PaypalApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }
    }

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken.asStateFlow()

    suspend fun fetchAccessToken(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val authKey = PAYPAL_AUTH_KEY.encodeBase64()
            val response = client.post(urlString = PAYPAL_AUTH_ENDPOINT) {
                headers {
                    append(HttpHeaders.Authorization, "Basic $authKey")
                    append(
                        HttpHeaders.ContentType,
                        ContentType.Application.FormUrlEncoded.toString()
                    )
                }
                setBody("grant_type=client_credentials")
            }

            if (response.status == HttpStatusCode.OK) {
                val tokenResponse = response.body<PaypalTokenResponse>()
                _accessToken.value = tokenResponse.accessToken
                onSuccess(tokenResponse.accessToken)
            } else {
                onError("Error while fetching an Access Token: ${response.status} -${response.bodyAsText()}")
            }
        } catch (e: Exception) {
            onError("Error while fetching an Access Token: ${e.message}")
        }
    }
}