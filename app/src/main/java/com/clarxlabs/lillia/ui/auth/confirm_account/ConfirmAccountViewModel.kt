package com.clarxlabs.lillia.ui.auth.confirm_account

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.lillia.core.repositories.AuthenticationRepository
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountError
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountInput
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountOutput
import com.clarxlabs.lillia.core.repositories.types.SendCodeError
import com.clarxlabs.lillia.core.repositories.types.SendCodeInput
import com.clarxlabs.lillia.core.repositories.types.SendCodeOutput
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class ConfirmAccountViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<ConfirmAccountModel.State, ConfirmAccountModel.Event>(
    ConfirmAccountModel.State(
        handle
    )
) {

    override fun sendEvent(event: ConfirmAccountModel.Event) {
        when (event) {
            is ConfirmAccountModel.Event.OnCodeChanged -> onCodeChanged(event.code.trim())
            is ConfirmAccountModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is ConfirmAccountModel.Event.OnSendCodeClicked -> onSendCodeClicked()
            is ConfirmAccountModel.Event.OnContactClicked -> onContactClicked(event.navigateTo)
        }
    }

    private fun onCodeChanged(text: String) {
        setState { it.copy(code = text.take(6), codeError = "") }
    }

    private fun onSendCodeClicked() {
        setState { it.copy(isLoading = true) }

        sendVerificationEmail()

        setState { it.copy(isLoading = false) }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        confirmEmailVerification { if (it.isRight) navigateTo(AppRoute.SignIn) }

        setState { it.copy(isLoading = false) }
    }

    private fun onContactClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = false) }
    }

    private fun sendVerificationEmail(onResponse: (Either<SendCodeError, SendCodeOutput>) -> Unit = {}) {
        val input = SendCodeInput(email = state.value.email)

        viewModelScope.launch { onResponse(authenticationRepository.verify(input)) }
    }

    private fun confirmEmailVerification(onResponse: (Either<ConfirmAccountError, ConfirmAccountOutput>) -> Unit) {
        val input = ConfirmAccountInput(email = state.value.email, code = state.value.code)

        viewModelScope.launch { onResponse(authenticationRepository.confirm(input)) }
    }
}
