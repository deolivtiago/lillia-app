package com.clarxlabs.lillia.core.datasources.factories

import android.util.Log
import com.clarxlabs.lillia.core.AppAPI
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient = HttpClient(OkHttp) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = AppAPI.BASE_URL
            }
            headers { append(HttpHeaders.Accept, ContentType.Application.Json.toString()) }
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 20_000L
            requestTimeoutMillis = 20_000L
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 5)
            exponentialDelay()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("debug", message)
                }
            }
            level = LogLevel.ALL
        }
    }
}
