package com.example.socialnetworkapp.presentation.register

import com.example.socialnetworkapp.utli.UiText

data class RegisterState(
    val successful: Boolean? = null,
    val message: UiText? = null,
    val isLoading: Boolean = false
)