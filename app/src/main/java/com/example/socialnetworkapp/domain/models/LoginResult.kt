package com.example.socialnetworkapp.domain.models

import com.example.socialnetworkapp.utli.SimpleResource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
