package com.clarxlabs.lillia.ui.auth.reset_password

import androidx.lifecycle.SavedStateHandle
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.AppViewModel

class ResetPasswordViewModel(
    handle: SavedStateHandle,
) : AppViewModel<ResetPasswordModel.State, ResetPasswordModel.Event>(
    ResetPasswordModel.State(handle)
) {

    override fun handleEvent(event: ResetPasswordModel.Event) {
        when (event) {
            is ResetPasswordModel.Event.OnCodeChanged ->
                onCodeChanged(event.text.trim())

            is ResetPasswordModel.Event.OnPasswordChanged ->
                onPasswordChanged(event.text)

            is ResetPasswordModel.Event.OnSubmitClicked ->
                onSubmitClicked(event.navigateTo)

            is ResetPasswordModel.Event.OnTogglePasswordVisibility ->
                onTogglePasswordVisibility()

            is ResetPasswordModel.Event.OnSendCodeClicked ->
                onSubmitClicked(event.navigateTo)

            is ResetPasswordModel.Event.OnContactClicked ->
                onSubmitClicked(event.navigateTo)

        }
    }

    private fun onCodeChanged(text: String) {
        setUiState { it.copy(code = text.take(6)) }
    }

    private fun onPasswordChanged(text: String) {
        setUiState { it.copy(password = text) }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        navigateTo(AppRoute.SignIn)
    }

    private fun onTogglePasswordVisibility() {
        setUiState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }
}
