package com.clarxlabs.lillia.ui.auth.verify_account

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import kotlinx.serialization.Serializable

enum class VerificationType { CONFIRM_ACCOUNT, RESET_PASSWORD, CHANGE_EMAIL }

sealed interface VerifyAccountModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val verificationType: VerificationType = VerificationType.CONFIRM_ACCOUNT,

        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<AppRoute.SendVerification>().email,
            verificationType = handle.toRoute<AppRoute.SendVerification>().verificationType
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnConfirmClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}

