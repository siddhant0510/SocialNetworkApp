package com.example.socialnetworkapp.presentation.feature_profile

data class UserItem(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
)
