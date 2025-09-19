package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.repository.AuthRepository
import com.example.socialnetworkapp.utli.SimpleResource

class AuthenticateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}