package com.clarxlabs.lillia.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AppViewModel<TState : AppModel.State, TEvent : AppModel.Event>(
    initialState: TState,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)

    val flow = _state.asStateFlow()

    protected val setUiState: ((TState) -> TState) -> Unit = _state::update
    protected val uiState = _state.value

    abstract fun handleEvent(event: TEvent)
}
