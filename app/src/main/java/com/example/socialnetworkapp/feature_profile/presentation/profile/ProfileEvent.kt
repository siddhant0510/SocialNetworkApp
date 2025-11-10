package com.example.socialnetworkapp.feature_profile.presentation.profile

sealed class ProfileEvent {
    data class GetProfile(val userId: String): ProfileEvent()
    data class LikedPost(val postId: String): ProfileEvent()
}