package com.clarxlabs.lillia.ui.auth.sign_in

import androidx.lifecycle.viewModelScope
import com.clarxlabs.lillia.core.repositories.AuthenticationRepository
import com.clarxlabs.lillia.core.repositories.types.SignInError
import com.clarxlabs.lillia.core.repositories.types.SignInInput
import com.clarxlabs.lillia.core.repositories.types.SignInOutput
import com.clarxlabs.lillia.core.services.ValidationService
import com.clarxlabs.lillia.core.services.validation.TextFieldValidation
import com.clarxlabs.lillia.core.services.validation.TextFieldValidation.Strategy
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.AppViewModel
import com.clarxlabs.lillia.ui.auth.verify_account.VerificationType
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validationService: ValidationService,
    private val authenticationRepository: AuthenticationRepository,
) : AppViewModel<SignInModel.State, SignInModel.Event>(SignInModel.State()) {

    init {
        setState { it.copy(isFormValid = isFormValid()) }
    }

    override fun sendEvent(event: SignInModel.Event) {
        when (event) {
            is SignInModel.Event.OnEmailChanged -> onEmailChanged(event.email.trim())
            is SignInModel.Event.OnPasswordChanged -> onPasswordChanged(event.password.trim())
            is SignInModel.Event.OnPasswordVisibilityClicked -> onPasswordVisibilityChanged()
            is SignInModel.Event.OnResetPasswordClicked -> onResetPasswordClicked(event.navigateTo)
            is SignInModel.Event.OnSignUpClicked -> onSignUpClicked(event.navigateTo)
            is SignInModel.Event.OnContactClicked -> onContactClicked()
            is SignInModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is SignInModel.Event.OnTermsClicked -> onTermsClicked(event.navigateTo)
            is SignInModel.Event.OnPoliciesClicked -> onPoliciesClicked()
        }
    }

    private fun onPoliciesClicked() {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onTermsClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }

        navigateTo(AppRoute.Home())
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true) }

        if (isFormValid())
            signIn {
                it.fold(
                    { error ->
                        if (error.email.contains("must be verified"))
                            navigateTo(
                                AppRoute.SendVerification(
                                    email = state.value.email,
                                    verificationType = VerificationType.CONFIRM_ACCOUNT,
                                )
                            )
                        else setState {
                            it.copy(
                                emailError = error.email.first(),
                                passwordError = error.password.first(),
                            )
                        }
                    },
                    { navigateTo(AppRoute.Home(it.accessToken, it.refreshToken)) }
                )
            }

        setState { it.copy(isLoading = false) }
    }

    private fun onContactClicked() {
        setState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onSignUpClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isPasswordVisible = false) }

        navigateTo(AppRoute.SignUp)
    }

    private fun onResetPasswordClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isPasswordVisible = false) }

        navigateTo(
            AppRoute.SendVerification(
                email = state.value.email,
                verificationType = VerificationType.RESET_PASSWORD,
            )
        )
    }

    private fun onPasswordVisibilityChanged() {
        setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun onEmailChanged(text: String) {
        setState {
            it.copy(
                email = text,
                emailError = validationService
                    .validate(text, Strategy.EMAIL)
                    .let(TextFieldValidation::getErrorMessage),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun onPasswordChanged(text: String) {
        setState {
            it.copy(
                password = text,
                passwordError = validationService
                    .validate(text, Strategy.PASSWORD)
                    .let(TextFieldValidation::getErrorMessage),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun signIn(onResponse: (Either<SignInError, SignInOutput>) -> Unit) {
        val input = SignInInput(
            email = state.value.email,
            password = state.value.password
        )

        viewModelScope.launch { onResponse(authenticationRepository.signIn(input)) }
    }

    private fun isFormValid(): Boolean =
        validationService.isValid(
            mapOf(
                Strategy.EMAIL.to(state.value.email),
                Strategy.PASSWORD.to(state.value.password),
            )
        )
}
