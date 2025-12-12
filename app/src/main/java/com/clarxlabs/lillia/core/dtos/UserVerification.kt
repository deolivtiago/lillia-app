package com.clarxlabs.lillia.core.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserVerification(val email: String = "") {
    @Serializable
    data class Error(val email: List<String> = emptyList())
}

