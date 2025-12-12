package com.clarxlabs.lillia.core

object AppAPI {
    const val BASE_URL = "lillia-api-ex.onrender.com"

    const val SIGN_IN = "/api/auth/sign-in"
    const val SIGN_UP = "/api/auth/sign-up"
    const val SIGN_OUT = "/api/auth/sign-out"
    const val SEND_CODE = "/api/auth/send-code"
    const val CONFIRM_ACCOUNT = "/api/auth/confirm-account"
    const val RESET_PASSWORD = "/api/auth/reset-password"
    const val CHANGE_EMAIL = "/api/auth/change-email"
    const val CHANGE_PASSWORD = "/api/auth/change-password"
    const val REFRESH_TOKEN = "/api/auth/refresh-token"
    const val USER_INFO = "/api/auth/user-info"
    const val LIST_USERS = "/api/users"
    const val LIST_ROLES = "/api/roles"
}
