package com.clarxlabs.lillia.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.clarxlabs.lillia.core.entities.User
import com.clarxlabs.lillia.ui.AppModel
import com.clarxlabs.lillia.ui.AppRoute
import io.konform.validation.Validation
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import kotlinx.serialization.Serializable

sealed interface HomeModel : AppModel {
    @Serializable
    data class State(
        val accessToken: String = "jwt.access.token",
        val refreshToken: String = "jwt.refresh.token",

        val user: User = User(),


        val isLoading: Boolean = false,
    ) : AppModel.State {
        constructor(handle: SavedStateHandle) : this(
            accessToken = handle.toRoute<AppRoute.Home>().accessToken,
            refreshToken = handle.toRoute<AppRoute.Home>().refreshToken,
        )

        var emailError: String = ""
        private var _email: String = "deoliv.tiago@gmail.com"

        var email: String
            get() = _email
            set(value) {
                _email = value
                val validate = Validation {
                    minLength(3)
                    maxLength(6)
                }

                emailError = if (validate(value).isValid) "" else "erro"
            }

    }

    sealed interface Event : AppModel.Event {
        data class OnAccessTokenChanged(val accessToken: String) : Event
        data class OnRefreshTokenChanged(val refreshToken: String) : Event
        data class OnSignOutClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnEmailChanged(val text: String) : Event
        data class OnSubmitClicked(val navigateTo: (AppRoute) -> Unit) : Event
        data class OnProfileClicked(val navigateTo: (AppRoute) -> Unit) : Event
    }
}
