package com.example.socialnetworkapp.presentation.feature_activity.data.remote

import com.example.socialnetworkapp.presentation.feature_activity.data.remote.dto.ActivityDto
import com.example.socialnetworkapp.utli.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/activity/get")
    suspend fun getActivities(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<ActivityDto>

    companion object {
        const val BASE_URL = "http://192.168.0.105:8001/"
    }
}