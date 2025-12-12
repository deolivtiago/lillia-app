package com.clarxlabs.lillia.ui.users.list

import androidx.lifecycle.viewModelScope
import com.clarxlabs.lillia.core.datasources.AuthenticationDataSource
import com.clarxlabs.lillia.core.entities.User
import com.clarxlabs.lillia.core.repositories.types.ListUsersError
import com.clarxlabs.lillia.core.repositories.types.ListUsersOutput
import com.clarxlabs.lillia.ui.AppViewModel
import it.czerwinski.kotlin.util.Either
import kotlinx.coroutines.launch

class ListViewModel(
    private val authenticationDataSource: AuthenticationDataSource,
) : AppViewModel<ListModel.State, ListModel.Event>(ListModel.State()) {

    init {
        fetchUsers(::mapResult)
    }

    override fun sendEvent(event: ListModel.Event) {
        when (event) {
            ListModel.Event.OnCardClicked -> {}
        }
    }

    fun fetchUsers(onResponse: (Either<ListUsersError, ListUsersOutput>) -> Unit = {}) {
        viewModelScope.launch { onResponse(authenticationDataSource.listUsers()) }
    }

    fun mapResult(result: Either<ListUsersError, ListUsersOutput>) =
        result.fold(
            { setState { it.copy(isLoading = false) } },
            { output ->
                setState {
                    it.copy(
                        isLoading = false,
                        users = output.map {
                            User(
                                id = it.id,
                                email = it.email,
                                fullName = it.fullName,
                                role = it.role,
                                avatarUrl = it.avatarUrl,
                                isVerified = it.isVerified,
                            )
                        }
                    )
                }
            }
        )
}
