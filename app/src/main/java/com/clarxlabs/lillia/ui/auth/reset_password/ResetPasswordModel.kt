package com.clarxlabs.lillia.ui.auth.reset_password

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import kotlinx.serialization.Serializable

sealed interface ResetPasswordModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val code: String = "",
        val codeError: String = "",
        val password: String = "",
        val passwordError: String = "",
        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false,
        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<AppRoute.ResetPassword>().email
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnCodeChanged(val text: String) : Event
        data class OnPasswordChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data object OnTogglePasswordVisibility : Event
        data class OnSendCodeClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
