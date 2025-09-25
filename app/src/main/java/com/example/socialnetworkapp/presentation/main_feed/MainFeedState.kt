package com.example.socialnetworkapp.presentation.main_feed

import com.example.socialnetworkapp.domain.models.Post

data class MainFeedState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false,
)
