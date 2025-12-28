package com.clarxlabs.lillia.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.clarxlabs.lillia.core.repositories.AuthenticationRepository
import com.clarxlabs.lillia.core.repositories.types.SignOutError
import com.clarxlabs.lillia.core.repositories.types.SignOutInput
import com.clarxlabs.lillia.core.repositories.types.SignOutOutput
import com.clarxlabs.lillia.core.repositories.types.UserInfoError
import com.clarxlabs.lillia.core.repositories.types.UserInfoInput
import com.clarxlabs.lillia.core.repositories.types.UserInfoOutput
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.AppViewModel
import io.konform.validation.Validation
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.pattern
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authenticationRepository: AuthenticationRepository,
    handle: SavedStateHandle,
) : AppViewModel<HomeModel.State, HomeModel.Event>(HomeModel.State(handle)) {

    init {
        getInfo { result ->
            result.fold(
                { setUiState { it.copy(user = it.user.copy(fullName = "error")) } },
                { user -> setUiState { it.copy(user = user) } }
            )
        }
    }


    private fun onEmailChanged(event: HomeModel.Event.OnEmailChanged) {
        setUiState { it.copy(email = event.text) }

        val validate = Validation {
            minLength(3)
            pattern("^\\w+@\\w+\\.\\w+$")
            maxLength(6)
        }

        Log.d("konform", validate(event.text).toString())
    }


    private fun signOut(onResponse: (Either<SignOutError, SignOutOutput>) -> Unit) {
        val input = SignOutInput(uiState.accessToken, uiState.refreshToken)

        viewModelScope.launch { onResponse(authenticationRepository.signOut(input)) }
    }

    private fun getInfo(onResponse: (Either<UserInfoError, UserInfoOutput>) -> Unit = {}) {
        val input = UserInfoInput(uiState.accessToken, uiState.refreshToken)

        viewModelScope.launch { onResponse(authenticationRepository.userInfo(input)) }
    }


    private fun onSignOutClicked(event: HomeModel.Event.OnSignOutClicked) {
        setUiState { it.copy(isLoading = true) }

        signOut { result ->
            if (result.isRight) {
                setUiState { it.copy(accessToken = "", refreshToken = "") }

                event.navigateTo(AppRoute.SignIn)
            }
        }

        setUiState { it.copy(isLoading = false) }
    }

    private fun onProfileClicked(event: HomeModel.Event.OnProfileClicked) {
        event.navigateTo(AppRoute.Profile(userId = uiState.user.id))
    }

    private fun onAccessTokenChanged(event: HomeModel.Event.OnAccessTokenChanged) {
        setUiState { it.copy(accessToken = event.accessToken) }
    }

    private fun onRefreshTokenChanged(event: HomeModel.Event.OnRefreshTokenChanged) {
        setUiState { it.copy(refreshToken = event.refreshToken) }
    }

    override fun handleEvent(event: HomeModel.Event) {
        when (event) {
            is HomeModel.Event.OnAccessTokenChanged -> onAccessTokenChanged(event)
            is HomeModel.Event.OnRefreshTokenChanged -> onRefreshTokenChanged(event)
            is HomeModel.Event.OnSignOutClicked -> onSignOutClicked(event)
            is HomeModel.Event.OnEmailChanged -> onEmailChanged(event)
            is HomeModel.Event.OnSubmitClicked -> {}
            is HomeModel.Event.OnProfileClicked -> onProfileClicked(event)
        }
    }


}

