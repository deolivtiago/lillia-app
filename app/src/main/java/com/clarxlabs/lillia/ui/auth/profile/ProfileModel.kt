package com.clarxlabs.lillia.ui.auth.profile

import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import kotlinx.serialization.Serializable

sealed interface ProfileModel : AppModel {
    @Serializable
    data class State(
        val email: String = "deoliv.tiago@gmail.com",
        val password: String = "4m1Mad?",
        val fullName: String = "Tiago de Oliveira",
        val passwordConfirmation: String = "4m1Mad??",

        val emailError: String = "",
        val passwordError: String = "",
        val fullNameError: String = "",
        val passwordConfirmationError: String = "",

        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false,

        val isLoading: Boolean = false,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data class OnEmailChanged(val text: String) : Event
        data class OnFullNameChanged(val text: String) : Event
        data class OnPasswordChanged(val text: String) : Event
        data class OnPasswordConfirmationChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data object OnPasswordVisibilityClicked : Event
        data class OnResetPasswordClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnSignInClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnTermsClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnPoliciesClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
