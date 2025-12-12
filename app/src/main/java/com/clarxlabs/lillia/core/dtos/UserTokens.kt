package com.clarxlabs.lillia.core.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTokens(
    @SerialName("access_token")
    val accessToken: String = "",

    @SerialName("refresh_token")
    val refreshToken: String = ""
) {
    @Serializable
    data class Error(
        @SerialName("access_token")
        val accessToken: List<String> = emptyList(),

        @SerialName("refresh_token")
        val refreshToken: List<String> = emptyList(),
    )
}
