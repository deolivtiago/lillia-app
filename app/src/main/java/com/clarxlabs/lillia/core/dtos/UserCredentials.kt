package com.clarxlabs.lillia.core.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val email: String = "",
    val password: String = "",
) {
    @Serializable
    data class Error(
        val email: List<String> = emptyList(),
        val password: List<String> = emptyList(),
    )
}
