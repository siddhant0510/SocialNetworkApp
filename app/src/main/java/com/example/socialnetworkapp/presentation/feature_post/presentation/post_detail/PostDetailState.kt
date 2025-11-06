package com.example.socialnetworkapp.presentation.feature_post.presentation.post_detail

import com.example.socialnetworkapp.domain.models.Comment
import com.example.socialnetworkapp.domain.models.Post

data class PostDetailState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false
)
