package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.repository.AuthRepository
import com.example.socialnetworkapp.utli.SimpleResource

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        return repository.register(email.trim(), username.trim(), password.trim())
    }
}