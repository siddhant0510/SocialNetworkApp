package com.example.socialnetworkapp.feature_auth.domain.models

import com.example.socialnetworkapp.feature_auth.presentation.util.AuthError
import com.example.socialnetworkapp.utli.SimpleResource

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
