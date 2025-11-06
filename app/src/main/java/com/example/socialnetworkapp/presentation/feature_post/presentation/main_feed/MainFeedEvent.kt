package com.example.socialnetworkapp.presentation.feature_post.presentation.main_feed

sealed class MainFeedEvent {
    object LoadMorePosts: MainFeedEvent()
    object LoadedPage: MainFeedEvent()
}