package com.example.socialnetworkapp.domain.models

data class Comment(
    val id: String,
    val username: String,
    val profilePictureUrl: String,
    val formattedTime: String,
    val comment: String,
    val isLiked: Boolean,
    val likedCount: Int
)
