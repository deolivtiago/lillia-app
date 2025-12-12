package com.clarxlabs.lillia.ui

import com.clarxlabs.lillia.ui.auth.verify_account.VerificationType
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object AuthGraph : AppRoute

    @Serializable
    data object SignIn : AppRoute

    @Serializable
    data object SignUp : AppRoute

    @Serializable
    data class SendVerification(
        val email: String = "",
        val verificationType: VerificationType = VerificationType.CONFIRM_ACCOUNT,
    ) : AppRoute

    @Serializable
    data class ConfirmAccount(val email: String = "") : AppRoute

    @Serializable
    data class ResetPassword(val email: String = "") : AppRoute

    @Serializable
    data class Home(val accessToken: String = "", val refreshToken: String = "") : AppRoute

    @Serializable
    data object ListUsers : AppRoute

    @Serializable
    data class Profile(val userId: String = "") : AppRoute
}
