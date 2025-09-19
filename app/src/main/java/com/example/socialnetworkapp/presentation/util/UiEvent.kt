package com.example.socialnetworkapp.presentation.util

import com.example.socialnetworkapp.utli.UiText

sealed class UiEvent {
    data class SnackbarEvent(val uiText: UiText): UiEvent()
    data class Navigate(val route: String): UiEvent()
}