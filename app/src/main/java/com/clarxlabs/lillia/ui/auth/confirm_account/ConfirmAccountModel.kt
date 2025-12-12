package com.clarxlabs.lillia.ui.auth.confirm_account

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import kotlinx.serialization.Serializable

sealed interface ConfirmAccountModel : AppModel {
    @Serializable
    data class State(
        val email: String = "invalid@mail.com",
        val code: String = "",

        val codeError: String = "",

        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            email = handle.toRoute<AppRoute.ConfirmAccount>().email
        )
    }

    sealed interface Event : AppModel.Event {
        data class OnCodeChanged(val code: String) : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnContactClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnSendCodeClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
