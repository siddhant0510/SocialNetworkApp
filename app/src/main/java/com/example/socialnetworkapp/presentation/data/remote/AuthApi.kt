package com.example.socialnetworkapp.presentation.data.remote

import com.example.socialnetworkapp.data.dto.response.BasicApiResponse
import com.example.socialnetworkapp.presentation.data.dto.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}