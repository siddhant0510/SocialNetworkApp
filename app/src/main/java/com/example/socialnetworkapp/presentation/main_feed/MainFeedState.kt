package com.example.socialnetworkapp.presentation.main_feed

import com.example.socialnetworkapp.domain.models.Post

data class MainFeedState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 0
)
