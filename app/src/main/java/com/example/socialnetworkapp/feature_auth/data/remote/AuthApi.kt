package com.example.socialnetworkapp.feature_auth.data.remote

import com.example.socialnetworkapp.data.dto.response.BasicApiResponse
import com.example.socialnetworkapp.presentation.data.dto.request.CreateAccountRequest
import com.example.socialnetworkapp.presentation.data.dto.request.LoginRequest
import com.example.socialnetworkapp.presentation.data.dto.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate(): Response<Unit>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}