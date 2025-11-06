package com.example.socialnetworkapp.presentation.feature_post.data.remote.dto

import com.example.socialnetworkapp.domain.models.Comment
import java.text.SimpleDateFormat
import java.util.Locale

data class CommentDto(
    val id: String,
    val username: String,
    val profilePictureUrl: String,
    val timestamp: Long,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int
) {
    fun toComment(): Comment {
        return Comment(
            id = id,
            username = username,
            profilePictureUrl = profilePictureUrl,
            formattedTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(timestamp),
            comment = comment,
            isLiked = isLiked,
            likedCount = likeCount
        )
    }
}
