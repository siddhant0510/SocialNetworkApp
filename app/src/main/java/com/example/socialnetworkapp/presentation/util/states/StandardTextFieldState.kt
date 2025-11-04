package com.example.socialnetworkapp.presentation.util.states

import com.example.socialnetworkapp.utli.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)
