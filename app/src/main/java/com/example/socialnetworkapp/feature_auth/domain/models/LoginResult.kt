package com.example.socialnetworkapp.feature_auth.domain.models

import com.example.socialnetworkapp.feature_auth.presentation.util.AuthError
import com.example.socialnetworkapp.utli.SimpleResource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
