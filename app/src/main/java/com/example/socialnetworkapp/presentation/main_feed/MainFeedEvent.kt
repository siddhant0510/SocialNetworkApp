package com.example.socialnetworkapp.presentation.main_feed

sealed class MainFeedEvent {
    object LoadMorePosts: MainFeedEvent()
    object LoadedPage: MainFeedEvent()
}