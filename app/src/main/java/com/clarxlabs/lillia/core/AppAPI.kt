package com.clarxlabs.lillia.core

object AppAPI {
    const val BASE_URL: String = "lillia.gigalixirapp.com"

    const val SIGNIN: String = "/api/auth/sign-in"
    const val SIGNUP: String = "/api/auth/sign-up"
    const val SIGNOUT: String = "/api/auth/sign-out"
    const val VERIFY: String = "/api/auth/send-code"
    const val CONFIRM: String = "/api/auth/confirm-account"
    const val USERINFO: String = "/api/auth/user-info"
    const val LIST_USERS: String = "/api/users"
}
