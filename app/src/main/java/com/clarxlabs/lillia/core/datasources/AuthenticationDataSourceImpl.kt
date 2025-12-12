package com.clarxlabs.lillia.core.datasources

import com.clarxlabs.lillia.core.AppAPI
import com.clarxlabs.lillia.core.datasources.factories.HttpRequestFactory
import com.clarxlabs.lillia.core.dtos.APIResponse
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountError
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountInput
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountOutput
import com.clarxlabs.lillia.core.repositories.types.ListUsersError
import com.clarxlabs.lillia.core.repositories.types.ListUsersOutput
import com.clarxlabs.lillia.core.repositories.types.SendCodeError
import com.clarxlabs.lillia.core.repositories.types.SendCodeInput
import com.clarxlabs.lillia.core.repositories.types.SendCodeOutput
import com.clarxlabs.lillia.core.repositories.types.SignInError
import com.clarxlabs.lillia.core.repositories.types.SignInInput
import com.clarxlabs.lillia.core.repositories.types.SignInOutput
import com.clarxlabs.lillia.core.repositories.types.SignOutError
import com.clarxlabs.lillia.core.repositories.types.SignOutInput
import com.clarxlabs.lillia.core.repositories.types.SignOutOutput
import com.clarxlabs.lillia.core.repositories.types.SignUpError
import com.clarxlabs.lillia.core.repositories.types.SignUpInput
import com.clarxlabs.lillia.core.repositories.types.SignUpOutput
import com.clarxlabs.lillia.core.repositories.types.UserInfoError
import com.clarxlabs.lillia.core.repositories.types.UserInfoInput
import com.clarxlabs.lillia.core.repositories.types.UserInfoOutput
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import it.czerwinski.kotlin.util.Either

class AuthenticationDataSourceImpl(private val httpClient: HttpClient) : AuthenticationDataSource {
    override suspend fun signIn(input: SignInInput): Either<SignInError, SignInOutput> {
        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.SIGN_IN).setBody(input)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun signUp(input: SignUpInput): Either<SignUpError, SignUpOutput> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.SIGN_UP).setBody(input)
            .execute().let { APIResponse.toEither(it) }

    override suspend fun signOut(input: SignOutInput): Either<SignOutError, SignOutOutput> {
        val queries = mapOf(
            "access_token" to input.accessToken,
            "refresh_token" to input.refreshToken,
        )

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Delete, AppAPI.SIGN_OUT).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun sendCode(input: SendCodeInput): Either<SendCodeError, SendCodeOutput> {
        val queries = mapOf("email".to(input.email))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.SEND_CODE).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun confirmAccount(input: ConfirmAccountInput): Either<ConfirmAccountError, ConfirmAccountOutput> {
        val queries = mapOf("email".to(input.email), "code".to(input.code))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.CONFIRM_ACCOUNT).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun userInfo(input: UserInfoInput): Either<UserInfoError, UserInfoOutput> {
        val headers = mapOf("authorization".to("Bearer ${input.accessToken}"))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.USER_INFO).setHeaders(headers)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun listUsers(): Either<ListUsersError, ListUsersOutput> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.LIST_USERS)
            .execute().let { APIResponse.toEither(it) }
}
