package com.example.socialnetworkapp.feature_activity.data.remote.dto

import com.example.socialnetworkapp.domain.models.Activity
import com.example.socialnetworkapp.feature_activity.domain.ActivityType
import java.text.SimpleDateFormat
import java.util.Locale

data class ActivityDto(
    val timestamp: Long,
    val userId: String,
    val parentId: String,
    val type: Int,
    val username: String,
    val id: String
) {
    fun toActivity(): Activity {
        return Activity(
            userId = userId,
            parentId = parentId,
            username = username,
            activityType = when (type) {
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.LikedComment.type -> ActivityType.CommentedOnPost
                else -> ActivityType.FollowedUser
            },
            formatedTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(timestamp)
        )
    }
}
