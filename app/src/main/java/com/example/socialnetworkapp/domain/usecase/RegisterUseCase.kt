package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.models.RegisterResult
import com.example.socialnetworkapp.domain.repository.AuthRepository
import com.example.socialnetworkapp.domain.util.ValidationUtil

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): RegisterResult {

        val emailError = ValidationUtil.validateEmail(email)
        val usernameError = ValidationUtil.validateUsername(username)
        val passwordError = ValidationUtil.validatePassword(password)

        if(emailError != null || usernameError != null || passwordError != null) {
            return RegisterResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
            )
        }

        val result = repository.register(email.trim(), username.trim(), password.trim())

        return RegisterResult(
            result = result
        )
    }
}