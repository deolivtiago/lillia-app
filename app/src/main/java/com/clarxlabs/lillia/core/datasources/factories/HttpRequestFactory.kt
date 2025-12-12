package com.clarxlabs.lillia.core.datasources.factories

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod

class HttpRequestFactory(val httpClient: HttpClient) {
    private var _url: String = "/invalid-url"
    private var _method: HttpMethod = HttpMethod.Get
    private var _headers: Map<String, String> = emptyMap()
    private var _queries: Map<String, String> = emptyMap()
    private var _body: Any? = ""

    fun create(method: HttpMethod, url: String) = apply {
        _method = method
        _url = url
    }

    fun setHeaders(headers: Map<String, String>) = apply { _headers = headers }
    fun setQueries(queries: Map<String, String>) = apply { _queries = queries }
    fun <T> setBody(body: T) = apply { _body = body }

    suspend fun execute() = httpClient.request(_url) {
        method = _method
        headers { _headers.forEach { append(it.key, it.value) } }
        url { _queries.forEach { parameters.append(it.key, it.value) } }
        setBody(_body)
    }
}
