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
        setUiState { it.copy(isFormValid = isFormValid()) }
    }

    override fun handleEvent(event: SignInModel.Event) {
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
        setUiState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onTermsClicked(navigateTo: (AppRoute) -> Unit) {
        setUiState { it.copy(isLoading = false, isPasswordVisible = false) }

        navigateTo(AppRoute.Home())
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setUiState { it.copy(isLoading = true) }

        if (isFormValid())
            signIn {
                it.fold(
                    { error ->
                        if (error.email.contains("must be verified"))
                            navigateTo(
                                AppRoute.SendVerification(
                                    email = flow.value.email,
                                    verificationType = VerificationType.CONFIRM_ACCOUNT,
                                )
                            )
                        else setUiState {
                            it.copy(
                                emailError = error.email.first(),
                                passwordError = error.password.first(),
                            )
                        }
                    },
                    { navigateTo(AppRoute.Home(it.accessToken, it.refreshToken)) }
                )
            }

        setUiState { it.copy(isLoading = false) }
    }

    private fun onContactClicked() {
        setUiState { it.copy(isLoading = false, isPasswordVisible = false) }
    }

    private fun onSignUpClicked(navigateTo: (AppRoute) -> Unit) {
        setUiState { it.copy(isPasswordVisible = false) }

        navigateTo(AppRoute.SignUp)
    }

    private fun onResetPasswordClicked(navigateTo: (AppRoute) -> Unit) {
        setUiState { it.copy(isPasswordVisible = false) }

        navigateTo(
            AppRoute.SendVerification(
                email = flow.value.email,
                verificationType = VerificationType.RESET_PASSWORD,
            )
        )
    }

    private fun onPasswordVisibilityChanged() {
        setUiState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun onEmailChanged(text: String) {
        setUiState {
            it.copy(
                email = text,
                emailError = validationService
                    .validate(text, Strategy.EMAIL)
                    .let(TextFieldValidation::getErrorMessage),
            )
        }

        setUiState { it.copy(isFormValid = isFormValid()) }
    }

    private fun onPasswordChanged(text: String) {
        setUiState {
            it.copy(
                password = text,
                passwordError = validationService
                    .validate(text, Strategy.PASSWORD)
                    .let(TextFieldValidation::getErrorMessage),
            )
        }

        setUiState { it.copy(isFormValid = isFormValid()) }
    }

    private fun signIn(onResponse: (Either<SignInError, SignInOutput>) -> Unit) {
        val input = SignInInput(
            email = flow.value.email,
            password = flow.value.password
        )

        viewModelScope.launch { onResponse(authenticationRepository.signIn(input)) }
    }

    private fun isFormValid(): Boolean =
        validationService.isValid(
            mapOf(
                Strategy.EMAIL.to(flow.value.email),
                Strategy.PASSWORD.to(flow.value.password),
            )
        )
}
