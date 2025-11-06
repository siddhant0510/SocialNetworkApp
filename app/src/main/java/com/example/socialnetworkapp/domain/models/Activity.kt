package com.example.socialnetworkapp.domain.models

import com.example.socialnetworkapp.presentation.feature_activity.domain.ActivityType

data class Activity(
    val userId: String,
    val parentId: String,
    val username: String,
    val activityType: ActivityType,
    val formatedTime: String,
)
