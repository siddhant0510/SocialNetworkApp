package com.example.socialnetworkapp.domain.state

import com.example.socialnetworkapp.feature_auth.presentation.util.AuthError
import com.example.socialnetworkapp.utli.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: AuthError? = null
)
