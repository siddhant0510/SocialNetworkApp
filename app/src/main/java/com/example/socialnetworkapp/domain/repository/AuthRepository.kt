package com.example.socialnetworkapp.domain.repository

import com.example.socialnetworkapp.presentation.data.dto.request.CreateAccountRequest
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource
}