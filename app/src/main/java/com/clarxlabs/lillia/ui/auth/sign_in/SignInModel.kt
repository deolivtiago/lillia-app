package com.clarxlabs.lillia.ui.auth.sign_in

import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import kotlinx.serialization.Serializable

sealed interface SignInModel : AppModel {
    @Serializable
    data class State(
        val email: String = "deoliv.tiago@gmail.com",
        val password: String = "4m1Mad?",

        val emailError: String = "",
        val passwordError: String = "",

        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false,

        val isLoading: Boolean = false,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data class OnEmailChanged(val email: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data object OnPasswordVisibilityClicked : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnResetPasswordClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data object OnContactClicked : Event
        data class OnTermsClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnPoliciesClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnSignUpClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
