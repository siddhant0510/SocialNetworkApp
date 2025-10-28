package com.example.socialnetworkapp.presentation.data

import com.example.socialnetworkapp.data.dto.response.BasicApiResponse
import com.example.socialnetworkapp.presentation.data.remote.ProfileResponse
import com.example.socialnetworkapp.presentation.edit_profile.SkillDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?,
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("/api/user/profile")
    suspend fun getSkills(): List<SkillDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}