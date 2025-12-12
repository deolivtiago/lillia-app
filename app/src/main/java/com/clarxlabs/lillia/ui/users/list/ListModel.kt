package com.clarxlabs.lillia.ui.users.list

import com.clarxlabs.lillia.core.entities.User
import com.clarxlabs.lillia.ui.AppModel

sealed interface ListModel {
    data class State(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = true,
    ) : AppModel.State

    sealed interface Event : AppModel.Event {
        data object OnCardClicked : Event
    }
}
