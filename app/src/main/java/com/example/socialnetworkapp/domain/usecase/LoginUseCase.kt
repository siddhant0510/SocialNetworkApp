package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.models.AuthError
import com.example.socialnetworkapp.domain.models.LoginResult
import com.example.socialnetworkapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
){

    suspend operator fun invoke(email: String, password: String): LoginResult {
        val emailError = if(email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(password.isBlank()) AuthError.FieldEmpty else null

        if(emailError != null || passwordError != null) {
            return LoginResult(emailError, passwordError)
        }

        return LoginResult(
            result = repository.login(email, password)
        )
    }
}