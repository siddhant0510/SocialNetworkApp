package com.example.socialnetworkapp.utilNew

import com.example.socialnetworkapp.utli.Event
import com.example.socialnetworkapp.utli.UiText

sealed class UiEvent : Event() {
    data class ShowSnakbar(val uiText: UiText): UiEvent()
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    object OnLogin: UiEvent()
}