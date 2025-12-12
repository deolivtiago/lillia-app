package com.clarxlabs.lillia.core.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val email: String = "",
    val password: String = "",

    @SerialName("full_name")
    val fullName: String = "",

    @SerialName("role_id")
    val role: String = "user",

    @SerialName("avatar_url")
    val avatarUrl: String = "",
) {
    @Serializable
    data class Error(
        val id: List<String> = emptyList(),
        val email: List<String> = emptyList(),
        val password: List<String> = emptyList(),

        @SerialName("full_name")
        val fullName: List<String> = emptyList(),

        @SerialName("role_id")
        val role: List<String> = emptyList(),

        @SerialName("avatar_url")
        val avatarUrl: List<String> = emptyList(),
    )
}
