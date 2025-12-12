package com.clarxlabs.lillia.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AppViewModel<TState : AppModel.State, TEvent : AppModel.Event>(
    initialState: TState,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    protected val setState = _state::update
    val state = _state.asStateFlow()

    abstract fun sendEvent(event: TEvent)
}
