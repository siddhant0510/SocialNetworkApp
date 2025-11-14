package com.example.socialnetworkapp.feature_auth.domain.repository

import com.example.socialnetworkapp.utli.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource
}
