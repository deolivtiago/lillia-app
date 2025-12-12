package com.clarxlabs.lillia.core.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",

    @SerialName("full_name")
    val fullName: String = "",

    @SerialName("role_id")
    val role: String = "user",

    @SerialName("avatar_url")
    val avatarUrl: String = "",

    @SerialName("is_verified")
    val isVerified: Boolean = false
)
