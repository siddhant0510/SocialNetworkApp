package com.example.socialnetworkapp.presentation.feature_activity.data.remote.dto

data class Activity(
    val timestamp: Long,
    val byUserId: String,
    val toUserId: String,
    val type: Int,
    val parentId: String,
    val id: String
)
