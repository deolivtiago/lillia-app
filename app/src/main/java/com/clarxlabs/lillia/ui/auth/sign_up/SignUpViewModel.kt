package com.clarxlabs.lillia.ui.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.clarxlabs.lillia.core.repositories.AuthenticationRepository
import com.clarxlabs.lillia.core.repositories.types.SignUpError
import com.clarxlabs.lillia.core.repositories.types.SignUpInput
import com.clarxlabs.lillia.core.repositories.types.SignUpOutput
import com.clarxlabs.lillia.core.services.ValidationService
import com.clarxlabs.lillia.core.services.validation.TextFieldValidation
import com.clarxlabs.lillia.core.services.validation.TextFieldValidation.Strategy
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val validationService: ValidationService,
) : AppViewModel<SignUpModel.State, SignUpModel.Event>(SignUpModel.State()) {

    init {
        setState { it.copy(isFormValid = isFormValid()) }
    }

    override fun sendEvent(event: SignUpModel.Event) {
        when (event) {
            is SignUpModel.Event.OnEmailChanged -> onEmailChanged(event.text.trim())
            is SignUpModel.Event.OnFullNameChanged -> onNameChanged(event.text)
            is SignUpModel.Event.OnPasswordChanged -> onPasswordChanged(event.text.trim())
            is SignUpModel.Event.OnPasswordConfirmationChanged -> onConfirmationChanged(event.text.trim())
            is SignUpModel.Event.OnSubmitClicked -> onSubmitClicked(event.navigateTo)
            is SignUpModel.Event.OnPasswordVisibilityClicked -> onPasswordVisibilityClicked()
            is SignUpModel.Event.OnContactClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnPoliciesClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnResetPasswordClicked -> onNavigate(event.navigateTo)
            is SignUpModel.Event.OnSignInClicked -> onNavigate(event.navigateTo, AppRoute.SignIn)
            is SignUpModel.Event.OnTermsClicked -> onNavigate(event.navigateTo)
        }
    }

    private fun onNameChanged(text: String) {
        setState {
            it.copy(
                fullName = getFullName(text),
                fullNameError = validationService
                    .validate(getFullName(text), Strategy.FULL_NAME)
                    .let(TextFieldValidation::getErrorMessage),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun getFullName(text: String): String = "${getFirstName(text)} ${getLastName(text)}"

    private fun getFirstName(fullName: String): String =
        fullName.replace("  ", " ").substringBefore(" ").trim().replaceFirstChar { it.uppercase() }

    private fun getLastName(fullName: String): String =
        fullName.replace("  ", " ").substringAfter(" ", "").trimStart().split(" ")
            .joinToString(" ") { if (it.length > 2) it.replaceFirstChar { it.uppercase() } else it }

    private fun onEmailChanged(text: String) {
        setState {
            it.copy(
                email = text,
                emailError = validationService
                    .validate(text, Strategy.EMAIL)
                    .let(TextFieldValidation::getErrorMessage)
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
                passwordConfirmationError = confirmationError(text, it.passwordConfirmation),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun confirmationError(password: String, confirmation: String): String =
        "A senha e a confirmação devem ser iguais"
            .takeIf { password != confirmation }
            .orEmpty()

    private fun onConfirmationChanged(text: String) {
        setState {
            it.copy(
                passwordConfirmation = text,
                passwordConfirmationError = confirmationError(text, it.password),
            )
        }

        setState { it.copy(isFormValid = isFormValid()) }
    }

    private fun onSubmitClicked(navigateTo: (AppRoute) -> Unit) {
        setState { it.copy(isLoading = true, isPasswordVisible = false) }

        if (isFormValid())
            signUp {
                it.fold(
                    { error ->
                        setState {
                            it.copy(
                                fullName = error.fullName.first(),
                                email = error.email.first(),
                                password = error.password.first(),
                            )
                        }
                    },
                    { navigateTo(AppRoute.SendVerification(state.value.email)) },
                )

            }

        setState { it.copy(isLoading = false) }
    }

    private fun onPasswordVisibilityClicked() {
        setState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun onNavigate(navigateTo: (AppRoute) -> Unit, route: AppRoute = AppRoute.Home()) {
        setState { it.copy(isPasswordVisible = false) }

        navigateTo(route)
    }

    private fun signUp(onResponse: (Either<SignUpError, SignUpOutput>) -> Unit) {
        val input = SignUpInput(
            fullName = state.value.fullName.trimEnd(),
            email = state.value.email,
            password = state.value.password,
        )

        viewModelScope.launch { onResponse(authenticationRepository.signUp(input)) }
    }

    private fun isFormValid(): Boolean =
        confirmationError(state.value.password, state.value.passwordConfirmation)
            .isEmpty() and validationService.isValid(
            mapOf(
                Strategy.EMAIL.to(state.value.email),
                Strategy.PASSWORD.to(state.value.password),
                Strategy.FULL_NAME.to(state.value.fullName),
            )
        )

}
