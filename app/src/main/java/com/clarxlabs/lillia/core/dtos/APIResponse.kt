package com.clarxlabs.lillia.core.dtos

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right
import kotlinx.serialization.Serializable

sealed interface APIResponse<out T> {
    @Serializable
    data class Data<out T>(val data: T) : APIResponse<T>

    @Serializable
    data class Error<out T>(val errors: T) : APIResponse<T>


    companion object {
        suspend inline fun <reified L, reified R> toEither(it: HttpResponse): Either<L, R> =
            when (it.status.value) {
                in 200..201 -> {
                    it.body<Data<R>>().data.let(::Right)
                }

                in 204..204 -> {
                    it.body<R>().let(::Right)
                }

                in 402..404 -> {
                    it.body<L>().let(::Left)
                }

                in 422..422 -> {
                    it.body<Error<L>>().errors.let(::Left)
                }

                else -> throw NotImplementedError("unmapped status code: ${it.status.value}")
            }
    }
}
